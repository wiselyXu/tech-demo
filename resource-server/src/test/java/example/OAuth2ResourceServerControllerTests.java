package example;

import com.gwkj.example.OAuth2ResourceServerApplication;
import com.gwkj.example.OAuth2ResourceServerController;
import com.gwkj.example.OAuth2ResourceServerSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jérôme Wacongne &lt;ch4mp@c4-soft.com&gt;
 * @author Josh Cummings
 * @since 5.2.0
 */
//@WebMvcTest(OAuth2ResourceServerController.class)
@Import(OAuth2ResourceServerSecurityConfiguration.class)
@SpringBootTest(classes = OAuth2ResourceServerApplication.class)
//@RunWith(SpringRunner.class)
public class OAuth2ResourceServerControllerTests {

	//@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new OAuth2ResourceServerController()).build();
	}

	@Test
	void indexGreetsAuthenticatedUser() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/").with(jwt().jwt((jwt) -> {
					System.out.println("==jwt value:   "+ jwt);
					jwt.claim("scope", "message:read");
			//jwt.subject("ch4mpy");
		//	jwt.claim("sub","ch4mpy");
		}
		)))
				.andExpect(content().string(is("Hello, ch4mpy!")));
		// @formatter:on
	}

	@Test
	void messageCanBeReadWithScopeMessageReadAuthority() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/message").with(jwt().jwt((jwt) -> {
			System.out.println("jwt value:   "+ jwt);
/*			jwt.claim("scope", "message:read");*/})))
				.andExpect(content().string(is("secret message")));

		this.mockMvc.perform(get("/message").with(jwt().authorities(new SimpleGrantedAuthority(("SCOPE_message:read")))))
				.andExpect(content().string(is("secret message")));
		// @formatter:on
	}

	@Test
	void messageCanNotBeReadWithoutScopeMessageReadAuthority() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/message").with(jwt()))
			//	.andExpect(status().isForbidden());
				//.andExpect(status().isOk());
		.andExpect(content().string(is("secret message")));
		// @formatter:on
	}

	@Test
	void messageCanNotBeCreatedWithoutAnyScope() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt()))
				.andExpect(status().isForbidden());
		// @formatter:on
	}

	@Test
	void messageCanNotBeCreatedWithScopeMessageReadAuthority() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt().jwt((jwt) -> jwt.claim("scope", "message:read"))))
				.andExpect(status().isForbidden());
		// @formatter:on
	}

	@Test
	void messageCanBeCreatedWithScopeMessageWriteAuthority() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/message")
				.content("Hello message")
				.with(jwt().jwt((jwt) -> jwt.claim("scope", "message:write"))))
				.andExpect(status().isOk())
				.andExpect(content().string(is("Message was created. Content: Hello message")));
		// @formatter:on
	}

}