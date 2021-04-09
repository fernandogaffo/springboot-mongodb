package com.fernandogaffo.springbootmongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.fernandogaffo.springbootmongodb.domain.Post;
import com.fernandogaffo.springbootmongodb.domain.User;
import com.fernandogaffo.springbootmongodb.dto.AuthorDTO;
import com.fernandogaffo.springbootmongodb.dto.CommentsDTO;
import com.fernandogaffo.springbootmongodb.repository.PostRepository;
import com.fernandogaffo.springbootmongodb.repository.UserRepository;

@Configuration
public class Instantiating implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo, abraços.",
				new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("21/03/2018"), "Bom dia", "Acordei feliz hoje.", new AuthorDTO(maria));

		CommentsDTO c1 = new CommentsDTO("Boa Viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentsDTO c2 = new CommentsDTO("Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentsDTO c3 = new CommentsDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));

		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));

		postRepository.saveAll(Arrays.asList(post1, post2));

		maria.getPosts().addAll(Arrays.asList(post1, post2));

		userRepository.save(maria);
	}

}
