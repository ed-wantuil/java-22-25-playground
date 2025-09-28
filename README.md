# Java 22–25 Playground 🚀

Playground de exemplos práticos das novidades do Java entre as versões **22 e 25**, com foco nos **JEPs definitivos**.  
Aqui você encontra código em formato **“antes e depois”**, mostrando como cada recurso impacta de forma direta o dia a dia.

## 📂 Estrutura

O projeto segue a organização por JEPs:  

- **Antes da JEP** → como era necessário escrever antes.  
- **Depois da JEP** → como fica com o recurso novo.  

Isso facilita comparar e entender rapidamente a evolução.

## 🔧 Como rodar

1. Clone o repositório:  

   ```bash
   git clone https://github.com/ed-wantuil/java-22-25-playground.git
   cd java-22-25-playground
   ```

2. Compile e execute os exemplos com Maven:  

   ```bash
   mvn clean package
   java -cp target/classes com.wantuil.jep.<NomeDaClasse>
   ```

3. Explore os pacotes em `src/main/java/com/wantuil/jep/`.

⚠️ **Requisitos**: JDK 25 (ou versões intermediárias conforme a JEP que você quiser testar).

## 📖 Artigo relacionado

Este playground complementa o artigo que detalha todas as mudanças do Java 21 → 25 em um guia prático:

👉 [Link do artigo aqui](https://dev.to/ed-wantuil/java-25-tudo-que-mudou-desde-o-java-21-em-um-guia-pratico-1b5n)

## 💡 Por que esse repo existe?

- Mostrar de forma clara os **benefícios das novas JEPs**.  
- Ajudar quem está planejando **migrar para o Java 25** a entender o impacto real no código.  
- Ser um recurso didático para aprender e ensinar Java moderno.

## 🤝 Contribuições

Sugestões e melhorias são bem-vindas!  
Abra uma issue ou envie um PR se quiser contribuir com exemplos adicionais.
