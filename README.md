📄 Gerador de Currículos - Backend API
📋 Sobre o Projeto
API REST desenvolvida em Java 21 com Spring Boot para geração automática de currículos em PDF. A API recebe dados estruturados em JSON e retorna um PDF formatado e pronto para uso profissional.

🚀 Tecnologias
Java 21 - Linguagem principal

Spring Boot 3.x - Framework web

OpenPDF 1.3.30 - Geração de documentos PDF

Maven - Gerenciamento de dependências

📁 Estrutura do Projeto
text
src/main/java/com/cvgenerator/
├── CurriculoApplication.java          # Classe principal
├── controller/
│   └── CvController.java             # Endpoints REST
├── model/
│   ├── CvRequest.java                # DTO de entrada
│   ├── Experiencia.java              # Modelo de experiência
│   └── Formacao.java                 # Modelo de formação
└── service/
    └── PdfService.java               # Lógica de geração de PDF
🔧 Configuração
Pré-requisitos
Java 21 ou superior

Maven 3.6+

IDE (IntelliJ, Eclipse ou VS Code)

📊 Endpoints da API
1. ✅ Health Check
text
GET http://localhost:8080/api/teste
Resposta: "API de Gerador de CV está funcionando!"

2. 📄 Gerar Currículo (PRINCIPAL)
text
POST http://localhost:8080/api/generate-cv
Content-Type: application/json
Exemplo de Request:

json
{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "(11) 99999-9999",
  "cargoDesejado": "Desenvolvedor Java",
  "resumoProfissional": "Desenvolvedor com 3 anos de experiência...",
  "experiencias": [
    {
      "empresa": "Tech Solutions",
      "cargo": "Dev Java Jr",
      "periodo": "2022-2023",
      "descricao": "Desenvolvimento de APIs REST"
    }
  ],
  "formacoes": [
    {
      "curso": "Análise de Sistemas",
      "instituicao": "Universidade XPTO",
      "anoConclusao": "2022"
    }
  ],
  "habilidades": ["Java", "Spring Boot", "Git"]
}
Resposta: application/pdf (download automático)

⚙️ Configuração do Ambiente
Arquivo application.properties
properties
server.port=8080
spring.application.name=cv-generator-api
logging.level.com.cvgenerator=INFO
Dependências Maven (pom.xml)
xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.github.librepdf</groupId>
    <artifactId>openpdf</artifactId>
    <version>1.3.30</version>
</dependency>
🧪 Testando a API
Com Postman/Insomnia
Configure método POST para http://localhost:8080/api/gerar-cv

Adicione header: Content-Type: application/json

Cole o JSON de exemplo no body

Envie a requisição

O PDF será baixado automaticamente

🔍 Características do PDF Gerado
✅ Layout profissional com cores padronizadas

✅ Seções organizadas (Contato, Resumo, Experiência, Formação, Habilidades)

✅ Fontes Arial/Helvetica para melhor legibilidade

✅ Margens e espaçamento adequados para impressão

✅ Cabeçalho e rodapé com informações de contato

✅ Design responsivo no documento PDF

![cv3](https://github.com/user-attachments/assets/f71cd58a-bc65-43f9-8d98-d2afb654fe17)
