# Spring Security (Java + Spring Boot)
# 1. Authentication vs Authorization
# Authentication
# Verifies who the user is.
# Examples:
# - Username + Password
# - JWT Token
# - OAuth Login
UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
                username,
                password
        );
# Authorization
# Determines what the user can access.
# Examples:
# - ADMIN can delete users
# - USER can view profile only
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) {
}
# Authentication = Identity
# Authorization = Permission

# 2. SecurityFilterChain
# Spring Security 6 uses SecurityFilterChain instead of WebSecurityConfigurerAdapter.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
# Flow
Request 
↓ 
SecurityFilterChain 
↓ 
Authentication 
↓ 
Authorization 
↓ 
Controller

# 3. Security Filters
# Spring Security works using a chain of filters.
# Common filters:
SecurityContextHolderFilter 
↓ 
UsernamePasswordAuthenticationFilter 
↓ 
BasicAuthenticationFilter 
↓ 
ExceptionTranslationFilter 
↓ 
AuthorizationFilter

# Example:
http.addFilterBefore(
    jwtFilter,
    UsernamePasswordAuthenticationFilter.class
);
# Custom JWT filters are often added before the username/password filter.

# 4. Password Hashing
# Never store passwords in plain text.
# Bad:
password = "admin123";
# Good:
password = "$2a$10$...";
# Hashing properties:
# - One-way
# - Irreversible
# - Secure storage
# Popular Algorithms:
# BCrypt ✅
# Argon2 ✅
# PBKDF2 ✅
# SHA-256 ❌ (alone not recommended)

# 5. BCrypt
# Most commonly used password encoder.
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
# Encoding:
String encoded =
        passwordEncoder.encode("admin123");
# Verification:
passwordEncoder.matches(
        "admin123",
        encoded
);
# Output:
$2a$10$Vv5K...
# Benefits:
# - Salt included automatically
# - Slow by design
# - Resistant to brute-force attacks

# 6. UserDetailsService
# Loads user information from database.
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        return User.builder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
    }
}
# Spring Security calls this automatically during authentication.

# 7. AuthenticationManager
# Responsible for authentication.
@Bean
AuthenticationManager authenticationManager(
        AuthenticationConfiguration config)
        throws Exception {

    return config.getAuthenticationManager();
}

# Authentication:
Authentication auth =
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

# Successful authentication returns:
Authentication
# Failed authentication throws:
BadCredentialsException

# 8. Role-Based Authorization
# Roles
ROLE_USER 
ROLE_ADMIN 
ROLE_MANAGER
# Configuration:
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin/**")
    .hasRole("ADMIN")
    .requestMatchers("/user/**")
    .hasAnyRole("USER", "ADMIN")
)
# User: .roles("ADMIN")
# Spring automatically adds: ROLE_ADMIN

# 9. Method-Level Security
# Enable:
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
}
# PreAuthorize
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser() {
}
# Multiple Roles
@PreAuthorize(
        "hasAnyRole('ADMIN','MANAGER')")
# Ownership Check
@PreAuthorize("#id == authentication.principal.id")
# Common annotations:
@PreAuthorize
@PostAuthorize
@PreFilter
@PostFilter
@Secured

# 10. JWT (JSON Web Token)
# A stateless authentication mechanism.
# Structure: Header.Payload.Signature
# Example:
eyJhb... 
. 
eyJzdWIiOi... 
. 
Ks84df...

# JWT Payload
{
  "sub": "aman",
  "role": "ADMIN",
  "exp": 1710000000
}
# Benefits: Stateless, Fast, Scalable
# Drawbacks: Difficult revocation, Token size

# 11. Access Token & Refresh Token
# Access Token
# Short life.
15 minutes
# Used for API access.
# Refresh Token
# Long life.
7 days 
30 days
# Used to obtain new access tokens.
# Flow:
Login 
↓ 
Access Token 
↓ 
Refresh Token 
↓ 
Access token expires 
↓ 
Use refresh token 
↓ 
New access token

# Example Response:
{
  "accessToken":"...",
  "refreshToken":"..."
}

# 12. OAuth 2.0
# Framework for delegated authorization.
# Example:
Login with Google 
Login with GitHub 
Login with Facebook
# Roles:
# Resource Owner
# - User
# Client
# - Your Application
# Authorization Server
# - Google
# Resource Server
# - API
# Flow:
User 
↓ 
Google Login 
↓ 
Authorization Code 
↓ 
Access Token 
↓ 
API Access

# 13. OpenID Connect (OIDC)
# Extension of OAuth 2.0.
# OAuth: Authorization
# OIDC: Authentication + Authorization
# Provides:
# - ID Token
# - User identity
# - Profile details
# Example ID Token:
{
  "sub":"123",
  "email":"aman@gmail.com"
}

# 14. OAuth2 Resource Server
# Validates access tokens received from clients.
# Dependency:
<dependency>
    <groupId>
        org.springframework.boot
    </groupId>
    <artifactId>
        spring-boot-starter-oauth2-resource-server
    </artifactId>
</dependency>
# Configuration:
@Bean
public SecurityFilterChain filterChain(
        HttpSecurity http)
        throws Exception {

    http.oauth2ResourceServer(
        oauth ->
            oauth.jwt(Customizer.withDefaults())
    );

    return http.build();
}

# 15. Session Management
# Traditional Web Applications:
User Login 
↓ 
Session Created 
↓ 
JSESSIONID Cookie
# Configuration:
http.sessionManagement(
    session ->
        session.sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED
        )
);
# For JWT APIs: SessionCreationPolicy.STATELESS

# 16. CSRF (Cross-Site Request Forgery)
# Attack where a malicious site forces users to perform actions.
# Example: https://bank.com/transfer
# Protection: http.csrf(Customizer.withDefaults());
# Disable for REST APIs: http.csrf(csrf -> csrf.disable());
# Typically done when using JWT-based stateless APIs.

# 17. CORS (Cross-Origin Resource Sharing)
# Problem:
Frontend: http://localhost:3000 
Backend: http://localhost:8080

# Different origins.
# Configuration:
@Bean
CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration config =
            new CorsConfiguration();

    config.setAllowedOrigins(
            List.of("http://localhost:3000"));

    config.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE"));

    config.setAllowedHeaders(
            List.of("*"));

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
            "/**",
            config);

    return source;
}
# Enable: http.cors(Customizer.withDefaults());

# 18. Secure Password Reset
# Recommended Flow:
Forgot Password 
↓ 
Generate Random Token 
↓ 
Store Hash of Token 
↓ 
Send Email Link 
↓ 
Validate Token 
↓ 
Set New Password

# Generate Token: String token =
        UUID.randomUUID().toString();
# Store: tokenHash = encoder.encode(token);
# Best Practices:
# - Expiration (15-30 min)
# - Single use
# - HTTPS only
# - Hash token before storing

# 19. API Security Best Practices
# Authentication
# ✅ JWT/OAuth2
# HTTPS
# ✅ Always use TLS
# Password Storage
# ✅ BCrypt
# Validation
# ✅ Validate all inputs
# Rate Limiting
# ✅ Prevent abuse
# Least Privilege
# ✅ Minimum permissions
# Secure Headers
http.headers(headers ->
        headers
            .frameOptions(
                    frame -> frame.deny()
            )
);
# Logging
# Log:
User Login
Access Denied
Failures
# Do Not Log:
Passwords
Access Tokens
Secrets

# 20. Secrets and Key Management
# Never hardcode secrets.
# ❌ Bad
String secret =
        "my-secret-key";
# ✅ Better
jwt.secret=${JWT_SECRET}
# Environment Variable:
export JWT_SECRET=abc123
# Access:
@Value("${jwt.secret}")
private String secret;
# Enterprise Solutions:
# - AWS Secrets Manager
# - Azure Key Vault
# - HashiCorp Vault
# - Google Secret Manager

# Spring Security Architecture (Interview)
Request 
↓ 
SecurityFilterChain 
↓ 
AuthenticationManager 
↓ 
UserDetailsService 
↓ 
PasswordEncoder (BCrypt) 
↓ 
Authentication Success 
↓ 
Authorization 
↓ 
Controller 
↓ 
Response

# Most Important Interview Questions
1. Authentication vs Authorization?
2. What is SecurityFilterChain?
3. What is UserDetailsService?
4. What is AuthenticationManager?
5. Why BCrypt instead of SHA-256?
6. How JWT works?
7. Access token vs Refresh token?
8. OAuth2 vs OpenID Connect?
9. What is CSRF?
10. What is CORS?
11. Stateful vs Stateless Authentication?
12. How does Spring Security filter chain work?
13. What is Method-Level Security?
14. How do you secure REST APIs in Spring Boot?
15. How does Role-Based Authorization work?

# These 15 questions cover most Spring Security interview rounds for Java/Spring Boot developers.