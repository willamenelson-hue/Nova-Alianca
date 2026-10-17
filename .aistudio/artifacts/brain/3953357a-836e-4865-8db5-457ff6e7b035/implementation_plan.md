# Igreja Batista Nova Aliança (IBNA) - Aplicativo Oficial

Aplicativo Android completo da Igreja Batista Nova Aliança projetado para fortalecer a fé comunitária, disponibilizando Bíblia offline (Almeida Corrigida Fiel) com áudio TTS (voz masculina e feminina), devocionais e mensagens pastorais, hinários tradicionais (Harpa Cristã e Cantor Cristão), letras de louvores, mural interativo de oração, agenda de eventos, contribuições seguras (Pix) e recursos de estudo bíblico avançados.

## Decisões Confirmadas com o Usuário

> [!IMPORTANT]
> As escolhas arquiteturais e de design foram alinhadas e confirmadas:

- **Distribuição Bíblica**: A versão Almeida Corrigida Fiel (ACF) estará nativamente integrada e pronta para uso 100% offline no dispositivo. Módulos adicionais em Espanhol (Reina-Valera) e Inglês (KJV) contarão com suporte para download e importação de bases estruturadas via link/nuvem.
- **Camada de Comunidade e Pastoral**: Mensagens em áudio/texto do pastor, devocionais, agenda da igreja e pedidos de oração serão orquestrados através de um repositório sincronizado em nuvem em tempo real com mural de oração interativo.
- **Identidade Visual**: Paleta nobre e espiritual com Azul Real Profundo (`#0E2A47`), Dourado Aquece (`#D4AF37`), tons neutros suaves e fundo de leitura com modo pergaminho e modo escuro OLED para máximo conforto visual.

---

## 1. Visão Geral e Conceito Central

- **O que faz**: Oferece aos membros e visitantes da Igreja Batista Nova Aliança um portal diário de espiritualidade e conexão com a igreja local. Inclui leitura bíblica com síntese de voz (TTS do Google), hinários clássicos, anotações de sermões, pedidos de oração da comunidade, calendário ministerial, dízimos e ofertas com chave Pix e gerador de QR Code, além de um Quiz bíblico e mapa de locais bíblicos históricos.
- **Público-alvo**: Membros da congregação, liderança pastoral, jovens, famílias e visitantes em busca de comunhão cristã e crescimento espiritual diário.
- **Valor Chave**: Experiência fluida tanto online (comunidade, eventos, orações) quanto offline (leitura da palavra no templo ou viagens, hinários e anotações pessoais).

---

## 2. Experiência do Usuário e Design Visual

### Fluxos Principais do Usuário

1. **Início (Home & Comunhão)**:
   - Card hero com o *Versículo do Dia* (com botão para compartilhar imagem/texto).
   - Acesso rápido à *Última Mensagem Pastoral* e devocional matinal.
   - Atalhos para *Bíblia*, *Hinários*, *Orações* e *Dízimos/Ofertas*.
   - Próximos eventos da congregação (Culto de Celebração, EBD, Vigília, Reunião de Jovens).

2. **Leitor Bíblico & Áudio TTS**:
   - Navegação rápida por Livro, Capítulo e Versículo.
   - Leitura fluida sem distrações com opções de tamanho de fonte, espaçamento e temas de cor (Branco, Séphia/Pergaminho e Escuro).
   - Player de Áudio TTS Google integrado: controle de reprodução (Play, Pause, Stop), seleção de voz (Masculina ou Feminina), velocidade de fala (0.75x a 1.5x) e destaque dinâmico de versículos durante a leitura.
   - Marcação de texto com 4 cores bíblicas (Amarelo, Verde, Azul, Rosa) e bloco de anotações por versículo.
   - Seletor de versões (ACF em Português nativa; painel para alternar/importar Reina-Valera e KJV).

3. **Planos de Leitura & Temas do Cotidiano**:
   - Temas do cotidiano com passagens curadas: Ansiedade e Paz, Família, Esperança, Finanças sob a Luz Bíblica, Perdão, Liderança Cristã.
   - Planos cronológicos e por metas (ex: Novo Testamento em 3 meses, Bíblia inteira em 1 ano, X capítulos por dia) com checklist interativo e barra de progresso em porcentagem.

4. **Hinários & Letras Gospel**:
   - Harpa Cristã (hinos clássicos pentecostais e batistas) e Cantor Cristão com índice numérico e por título/autor.
   - Busca instantânea por número ou trecho da letra.
   - Guia de Louvores Contemporâneos com integração/link para letras.mus.br e visualizador de letras formatadas.

5. **Mural de Oração & Agenda Pastoral**:
   - Mural público de intercessão ("Orando Juntos"): fiéis podem publicar pedidos e membros tocam em "Estou Orando por Você" gerando apoio fraternal.
   - Opção de envio de pedidos confidenciais direto ao pastor.
   - Agenda mensal e semanal com horários dos cultos, localização do templo e lembretes push.

6. **Contribuições & Doações Transparentes**:
   - Interface segura para Dízimos, Ofertas de Amor, Missões e Fundo de Construção.
   - Chave Pix com cópia em 1 clique ("Pix Copia e Cola") e renderização de QR Code.
   - Orientações de transferência e comprovante virtual.

7. **Ferramentas de Estudo, Mapa e Quiz**:
   - Busca avançada (por Antigo/Novo Testamento, livros históricos, proféticos ou evangelhos).
   - Mapa bíblico interativo com pontos históricos de destaque (Jerusalém, Belém, Nazaré, Mar da Galileia, Monte Sinai) e rota para o templo da Igreja Batista Nova Aliança.
   - Quiz bíblico com perguntas divididas por níveis (Iniciante, Intermediário, Avançado), feedback imediato e pontuação.

### Identidade Visual e Temas

- **Cores Principais**:
  - `Primary`: Azul Real Profundo (`#0E2A47`) - Solenidade, reverência e paz.
  - `Secondary` / `Accent`: Dourado Real (`#D4AF37`) - Aliança, promessa e preciosidade.
  - `Tertiary`: Âmbar Suave (`#F4C430`) para medalhas e realces de destaque.
  - `Background`: Branco Marfim (`#F8F9FA`) no modo claro; Carvão Profundo (`#12161A`) no modo escuro.
  - `Reading Surface`: Papiro/Pergaminho acolhedor (`#FBF7EE`) para redução do cansaço visual.
- **Tipografia**: Material 3 elegante com fontes serifadas expressivas para versículos e títulos sagrados, e sans-serif limpa para menus e formulários.
- **Acessibilidade e Usabilidade**: Touch targets mínimos de 48dp, suporte completo a fontes ampliadas pelo sistema e navegação ergonômica inferior e por abas.

---

## 3. Principais Decisões de Arquitetura e Engenharia

1. **Motor Bíblico Offline Híbrido**:
   - *Abordagem*: SQLite / Room estruturado no aplicativo com os 66 livros da ACF pré-indexados e organizados por testamento, livro, capítulo e versículo, garantindo velocidade instantânea de busca e 0ms de latência mesmo sem internet.
   - *Suporte Multi-idioma*: Módulo gerenciador de traduções com suporte a download/importação de pacotes JSON para RV (Espanhol) e KJV (Inglês) a partir de link em nuvem configurável.
2. **Text-to-Speech (TTS) com Seleção de Gênero de Voz**:
   - *Abordagem*: Utilização da API nativa `android.speech.tts.TextToSpeech`, consultando as vozes instaladas no Google TTS engine para filtrar variantes masculinas e femininas, com controle de pitch e velocidade.
3. **Persistência de Dados do Usuário**:
   - *Local (Room)*: Marcações com cores, notas de versículos, histórico de progresso de leitura diária, favoritos de hinos e pontuação do quiz.
   - *Nuvem (Repositório Sincronizado)*: Mensagens pastorais, comunicados oficiais, mural de oração comunitário e eventos da agenda.
4. **Notificações**:
   - Canal de notificações dedicado ("Mensagens da Igreja" e "Versículo do Dia") com suporte a agendamento local e alertas pastorais.

---

## 4. Diagrama de Arquitetura do Sistema

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Jetpack Compose UI Layer                        │
├──────────────┬──────────────┬──────────────┬─────────────┬─────────────┤
│  Home / Dia  │ Bíblia & TTS │   Hinários   │ Oração/News │ Doações Pix │
└───────┬──────┴──────┬───────┴──────┬───────┴──────┬──────┴──────┬──────┘
        │             │              │              │             │
┌───────▼─────────────▼──────────────▼──────────────▼─────────────▼──────┐
│                    ViewModels & StateFlow Layer                        │
│   (BibleViewModel, ChurchViewModel, HymnsViewModel, StudyViewModel)    │
└───────────────────────────────┬────────────────────────────────────────┘
                                │
┌───────────────────────────────▼────────────────────────────────────────┐
│                     Repository & Service Layer                         │
├───────────────────────────────┼────────────────────────────────────────┤
│   Local Bible & Hymns Repo    │    Church Cloud & Community Repo       │
│   Text-to-Speech Service      │    Sync Manager (Google Drive/Cloud)   │
└───────────────┬───────────────┴───────────────────┬────────────────────┘
                │                                   │
┌───────────────▼───────────────┐   ┌───────────────▼────────────────────┐
│      Room SQLite Database     │   │      Cloud Services & Network      │
│  - Bíblia ACF Offline         │   │  - Mensagens Pastorais             │
│  - Hinários (Harpa/Cantor)    │   │  - Mural de Oração em Tempo Real   │
│  - Destaques, Notas, Planos   │   │  - Agenda de Eventos & Avisos      │
└───────────────────────────────┘   └────────────────────────────────────┘
```

---

## Próximos Passos de Execução (Após Aprovação)

1. **Configuração de Metadados e Tema**: Atualizar `metadata.json` e `strings.xml` com o nome oficial da Igreja Batista Nova Aliança e estruturar o tema de cores Real Navy & Warm Gold.
2. **Camada de Dados da Bíblia e Hinários**: Criar modelos de dados, DAOs do Room, carregador de dados offline (ACF, Harpa Cristã, Cantor Cristão) e importador de traduções.
3. **Mecanismo de Áudio TTS**: Desenvolver serviço de Text-to-Speech com detecção e alternância entre vozes masculina e feminina.
4. **Telas e Recursos Centrais**:
   - Leitor Bíblico com seleção de versículos, marcador colorido, anotações e TTS.
   - Dashboard inicial com Versículo do Dia e devocionais pastorais.
   - Módulo de Hinários e integração com Letras gospel.
   - Planos de leitura temáticos do cotidiano e checklist.
   - Mural de pedidos de oração e agenda de eventos.
   - Painel seguro de dízimos e ofertas com chave Pix e QR Code.
   - Quiz bíblico, mapa de pontos sagrados e busca global.
5. **Validação e Compilação**: Executar `compile_applet` para assegurar build perfeito e estabilidade.
