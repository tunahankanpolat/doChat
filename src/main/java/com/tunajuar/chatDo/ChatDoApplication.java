package com.tunajuar.chatDo;

import com.tunajuar.chatDo.dataAccess.abstracts.GroupUserRepository;
import com.tunajuar.chatDo.entities.concretes.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@SpringBootApplication
@EnableMongoRepositories
public class ChatDoApplication {
	public static void main(String[] args) {
	SpringApplication.run(ChatDoApplication.class, args);
	}
}
