import urllib.request
import json
import sqlite3
import os
import re
import time

print("Starting Bible database generation...")
start_time = time.time()

out_dir = "app/src/main/assets/databases"
os.makedirs(out_dir, exist_ok=True)
db_path = os.path.join(out_dir, "bibliaACF.db")
if os.path.exists(db_path):
    os.remove(db_path)

conn = sqlite3.connect(db_path)
cur = conn.cursor()

# 1. Exact Room schema for tables and indexes matching BibliaRoomDatabase_Impl
cur.execute('''
CREATE TABLE IF NOT EXISTS `versiculos` (
    `id` INTEGER NOT NULL,
    `versao` TEXT NOT NULL,
    `livro` INTEGER NOT NULL,
    `capitulo` INTEGER NOT NULL,
    `numero` INTEGER NOT NULL,
    `texto` TEXT NOT NULL,
    PRIMARY KEY(`id`, `versao`)
)
''')
cur.execute('CREATE INDEX IF NOT EXISTS `index_versiculos_versao_livro_capitulo` ON `versiculos` (`versao`, `livro`, `capitulo`)')
cur.execute('CREATE INDEX IF NOT EXISTS `index_versiculos_id` ON `versiculos` (`id`)')

cur.execute('''
CREATE TABLE IF NOT EXISTS `anotacoes` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `versiculoId` INTEGER NOT NULL,
    `textoAnotacao` TEXT NOT NULL,
    `dataCriacao` INTEGER NOT NULL
)
''')
cur.execute('CREATE INDEX IF NOT EXISTS `index_anotacoes_versiculoId` ON `anotacoes` (`versiculoId`)')

cur.execute('''
CREATE TABLE IF NOT EXISTS `marcacoes` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `versiculoId` INTEGER NOT NULL,
    `tipo` TEXT NOT NULL,
    `corHex` TEXT NOT NULL,
    `dataCriacao` INTEGER NOT NULL
)
''')
cur.execute('CREATE INDEX IF NOT EXISTS `index_marcacoes_versiculoId` ON `marcacoes` (`versiculoId`)')
cur.execute('CREATE UNIQUE INDEX IF NOT EXISTS `index_marcacoes_versiculoId_tipo` ON `marcacoes` (`versiculoId`, `tipo`)')

cur.execute('''
CREATE TABLE IF NOT EXISTS `referencias_cruzadas` (
    `origemVersiculoId` INTEGER NOT NULL,
    `destinoVersiculoId` INTEGER NOT NULL,
    `contextoDescricao` TEXT,
    PRIMARY KEY(`origemVersiculoId`, `destinoVersiculoId`)
)
''')
cur.execute('CREATE INDEX IF NOT EXISTS `index_referencias_cruzadas_origemVersiculoId` ON `referencias_cruzadas` (`origemVersiculoId`)')
cur.execute('CREATE INDEX IF NOT EXISTS `index_referencias_cruzadas_destinoVersiculoId` ON `referencias_cruzadas` (`destinoVersiculoId`)')

# Room master table with exact hash from Room compiler
cur.execute('CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY, identity_hash TEXT)')
cur.execute("INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES (42, 'd4ed80c703ab506ae5e5f994a1aebaf9')")

# 2. Insert Bible Verses (ACF, JFAA, JFAC)
versions = [
    ('ACF', 'https://raw.githubusercontent.com/thiagobodruk/bible/master/json/pt_acf.json', 'utf-8'),
    ('JFAA', 'https://raw.githubusercontent.com/thiagobodruk/bible/master/json/pt_aa.json', 'utf-8-sig'),
    ('JFAC', 'https://raw.githubusercontent.com/thiagobodruk/bible/master/json/pt_arc.json', 'utf-8')
]

for vcode, url, enc in versions:
    print(f"Fetching {vcode}...")
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    with urllib.request.urlopen(req) as resp:
        bdata = json.loads(resp.read().decode(enc))
    
    rows = []
    for b_idx, book in enumerate(bdata):
        book_id = b_idx + 1
        for c_idx, chap in enumerate(book['chapters']):
            chap_num = c_idx + 1
            for v_idx, txt in enumerate(chap):
                v_num = v_idx + 1
                v_id = (book_id * 1000000) + (chap_num * 1000) + v_num
                rows.append((v_id, vcode, book_id, chap_num, v_num, txt.strip()))
    
    cur.executemany('INSERT INTO versiculos VALUES (?, ?, ?, ?, ?, ?)', rows)
    print(f"Inserted {len(rows)} verses for {vcode}")

# 3. Insert TSK Cross-References
print("Fetching TSK cross references...")
book_map = {
    'ge': 1, 'ex': 2, 'le': 3, 'nu': 4, 'de': 5,
    'jos': 6, 'jd': 7, 'ru': 8, '1sa': 9, '2sa': 10,
    '1ki': 11, '2ki': 12, '1ch': 13, '2ch': 14, 'ezr': 15,
    'ne': 16, 'es': 17, 'job': 18, 'ps': 19, 'pr': 20,
    'ec': 21, 'ca': 22, 'isa': 23, 'jer': 24, 'la': 25,
    'eze': 26, 'da': 27, 'ho': 28, 'joe': 29, 'am': 30,
    'ob': 31, 'jon': 32, 'mic': 33, 'na': 34, 'hab': 35,
    'zep': 36, 'hag': 37, 'zec': 38, 'mal': 39,
    'mt': 40, 'mr': 41, 'lu': 42, 'joh': 43, 'ac': 44,
    'ro': 45, '1co': 46, '2co': 47, 'ga': 48, 'eph': 49,
    'php': 50, 'col': 51, '1th': 52, '2th': 53, '1ti': 54,
    '2ti': 55, 'tit': 56, 'phm': 57, 'heb': 58, 'jas': 59,
    '1pe': 60, '2pe': 61, '1jo': 62, '2jo': 63, '3jo': 64,
    'jude': 65, 're': 66
}

req_tsk = urllib.request.Request('https://raw.githubusercontent.com/ariseshinestudio/TSK/master/tskxref.txt', headers={'User-Agent': 'Mozilla/5.0'})
with urllib.request.urlopen(req_tsk) as resp:
    tsk_lines = resp.read().decode('utf-8', errors='ignore').splitlines()

ref_rows = []
seen = set()
for line in tsk_lines:
    parts = line.strip().split('\t')
    if len(parts) >= 6:
        b = int(parts[0])
        c = int(parts[1])
        v = int(parts[2])
        orig_id = (b * 1000000) + (c * 1000) + v
        phrase = parts[4].strip() if len(parts) > 4 and parts[4].strip() else None
        targets_str = parts[5]
        for item in targets_str.split(';'):
            item = item.strip()
            m = re.match(r'([0-9]?[a-zA-Z]+)\s+([0-9]+):([0-9]+)', item)
            if m:
                bk_abbr = m.group(1).lower()
                tgt_c = int(m.group(2))
                tgt_v = int(m.group(3))
                tgt_b = book_map.get(bk_abbr)
                if tgt_b:
                    dest_id = (tgt_b * 1000000) + (tgt_c * 1000) + tgt_v
                    pair = (orig_id, dest_id)
                    if pair not in seen:
                        seen.add(pair)
                        desc = f"TSK: {phrase}" if phrase else "Treasury of Scripture Knowledge"
                        ref_rows.append((orig_id, dest_id, desc))

cur.executemany('INSERT INTO referencias_cruzadas VALUES (?, ?, ?)', ref_rows)
print(f"Inserted {len(ref_rows)} TSK cross references")

conn.commit()
cur.execute('VACUUM')
conn.close()

db_size = os.path.getsize(db_path) / (1024 * 1024)
print(f"Done! Database generated at {db_path} ({db_size:.2f} MB) in {time.time()-start_time:.2f}s")
