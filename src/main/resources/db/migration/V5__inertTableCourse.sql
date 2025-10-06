-- ##################################################################
-- POPULANDO A TABELA COURSE
-- ##################################################################

-- CATEGORIA: PROGRAMAÇÃO
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('Lógica de programação', 'LOGICA', 'Aprenda os fundamentos essenciais de como um computador pensa.', 'joao.costal@alura.com', 1),
('.NET', 'DOTNET', 'Desenvolvimento de aplicações robustas com a plataforma Microsoft.', 'felipe.andrade@alura.com', 1),
('Automação e Produtividade', 'AUTOPROD', 'Scripts e ferramentas para otimizar seu dia a dia.', 'alberto.santos@alura.com', 1);

-- CATEGORIA: FRONT-END
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('HTML', 'HTML5', 'Estrutura fundamental de qualquer website.', 'carol.luz@alura.com', 2),
('CSS', 'CSS3', 'Estilização e design responsivo para todas as telas.', 'maria.alberta@alura.com', 2),
('Svelte', 'SVELTE', 'A framework JavaScript que compila seu código.', 'carol.luz@alura.com', 2),
('VueJS', 'VUE-JS', 'Framework progressivo para interfaces de usuário.', 'felipe.andrade@alura.com', 2);

-- CATEGORIA: DATA SCIENCE
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('SQL e Banco de Dados', 'SQL-DB', 'Consultas e gerenciamento de dados relacionais.', 'victor.cunha@alura.com', 3),
('Engenharia de Dados', 'ENG-DATA', 'Construção de pipelines e infraestrutura de dados.', 'victor.cunha@alura.com', 3),
('Análise de dados', 'ANALISE', 'Técnicas e ferramentas para extração de insights.', 'cezar.arquiles@alura.com', 3);

-- CATEGORIA: INTELIGÊNCIA ARTIFICIAL
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('IA para Criativos', 'IA-CRIATIV', 'Aplicações de IA para produção artística e design.', 'lucas.montano@alura.com', 4),
('IA para Programação', 'IA-PROG', 'Assistentes de código e ferramentas de desenvolvimento de IA.', 'galego.oficial@alura.com', 4),
('IA para Negócios', 'IA-NEGOCIO', 'Estratégias e modelos de IA para tomada de decisão.', 'pero.baptista@alura.com', 4);

-- CATEGORIA: DEVOPS
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('Linux', 'LINUX', 'Fundamentos do sistema operacional essencial para servidores.', 'anderson.freitas@alura.com', 5),
('FinOps', 'FIN-OPS', 'Gestão financeira e otimização de custos em cloud.', 'dev.instructor@alura.com', 5),
('Automação de Processos', 'AUTO-PROC', 'Criação de fluxos de trabalho e CI/CD.', 'dev.instructor@alura.com', 5);

-- CATEGORIA: UX & DESIGN
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('UI Design', 'UI', 'Princípios de design de interface e prototipação.', 'carol.luz@alura.com', 6),
('Design System', 'DESIGN-SYS', 'Criação e manutenção de bibliotecas de componentes.', 'mario.luiz@alura.com', 6),
('UX Writing', 'UX-WRITING', 'Microtextos e copy para guiar o usuário.', 'felipe.braga@alura.com', 6);

-- CATEGORIA: MOBILE
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('Flutter', 'FLUTTER', 'Desenvolvimento nativo e multiplataforma com Dart.', 'calor.luz@alura.com', 7),
('Android', 'ANDROID', 'Aplicações nativas para o ecossistema Google.', 'lucas.montano@alura.com', 7),
('iOS', 'IOS-DEV', 'Desenvolvimento de apps para dispositivos Apple.', 'antonio.junqueira@alura.com', 7);

-- CATEGORIA: PROGRAMAÇÃO (Adiciona mais 3 cursos)
INSERT INTO Course (name, code, description, instructorEmail, categoryId) VALUES
('Agilidade', 'AGILE', 'Princípios e práticas ágeis como Scrum e Kanban.', 'lucas.montano@alura.com', 8),
('Liderança', 'LIDERANCA', 'Desenvolvimento de habilidades de gestão e liderança técnica.', 'alberto.santos@alura.com', 8),
('Ensino e Aprendizagem', 'ENSINO', 'Técnicas de mentoria e crescimento profissional contínuo.', 'anderson.freitas@alura.com', 8);