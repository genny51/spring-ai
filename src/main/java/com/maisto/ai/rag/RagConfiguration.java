package com.maisto.ai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.util.List;


/*
In this class we define the ETL pipeline: READ -> TRAMSFORM -> WRITE
It's necessary to insert our informations in a vector database
*/
@Configuration
public class RagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    @Value("classpath:/data/models.json")
    private Resource models;

    private final String vectorStoreName = "vectorestore.json";

    @Bean
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel){

        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();//*1
        File vectoreStoreFile = getVectoreStoreFile();

        if(vectoreStoreFile.exists()){
            log.info("Vectore Store file already exist");
            vectorStore.load(vectoreStoreFile);
        }
        else {
            //ETL pipeline step by step:

            //read
            TextReader textReader = new TextReader(models);
            List<Document> documentList = textReader.get();

            //split
            TokenTextSplitter textSplitter = new TokenTextSplitter();
            List<Document> documentSplitted = textSplitter.apply(documentList);

            //write
            vectorStore.add(documentSplitted);

            //save(No step of ETL)
            vectorStore.save(vectoreStoreFile);
        }
        return vectorStore;


    }

    private File getVectoreStoreFile() {
        Path basePath = Path.of("src","main","resources","data");
        String absolutePath = basePath.toFile().getAbsolutePath() + "/" + vectorStoreName;
        return new File(absolutePath);

    }

    /*

    *1: SimpleVectoreStore :
    * it's an in-memory vectore db.
    * It stores embeddings into an internal hashmap (add method)
    * If we want to avoid at every launch the creation of embeddings to store in SimpleVectoreStore, we can save informations in a file once created (save method)
    * and then reuse it at every launch by loading it (load method)

    */
}
