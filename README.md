# ToffeeStory

## 개요
스타벅스의 음료를 다양한 토핑으로 커스터마이징 하여 만든 음료에 대한 정보를 공유하는 웹 서비스

## Member
- 남혁민
- 이세라
- 전유리

## 구조
- BackEnd
    - SpringBoot 사용
    - Front에 API 제공(RESTController로 구성) 
    - Server Port : 8098

- FrontEnd
    - axios 통신을 이용해서 BackEnd API를 호출하는 방식
    - Server Port : 8080
    
## AWS 주소
> http://ec2-13-125-236-65.ap-northeast-2.compute.amazonaws.com:8080/  

## 기술 스택
> Vue, Vuex, Vue-Cli, Docker, Java, Spring boot, JPA, Spring Security, JWT, Tailwind css, Git, Gradle, MySQL, AWS EC2

## AWS 접속 방법
> AWS 접속하기 위해서는 AWS 계정에서 자신의 IP를 변경 후 접속해야 한다.
  
- Mac OS 실행 방법
    - toffeestory.pem 파일을 ssh관리 폴더(~/.ssh/)에 복사 붙여넣기한다.
    - config 파일에 Host, HostName, User, pem파일 등록
    - ssh 등록한 Host이름으로 실행 (예: ssh toffeestory)
  
- Windows OS 실행 방법 
    - putty 설치 (혹은 비슷한 프로그램 설치)
    - toffeestory.pem 파일을 다운 받은 후 PuttyGen 프로그램 실행 후 pem 파일을 .ppk 파일로 변환
    - Putty 실행 후 Auth에 변환한 ppk 파일 등록 후 user@public IP 주소로 실행한다.

## 프로젝트 실행 방법
- BackEnd
    - Intellij IDEA 사용
        - BackendApplication 실행
    - jar 파일 사용
        - gradle로 build (./buildew clean build)
        - backend/build/libs 폴더 경로로 이동
        - java -jar 생성된파일.jar 실행
        
- FrontEnd
    - FrontEnd 폴더에서 npm run serve 실행

##주요 기능
- <Header>
    - <Join>
    - <UploadModal>
    - <Profile>
- <Home>
    - <ToffeeList> : 게시글 목록 컴포넌트
        - unsplash API로 스타벅스 데이터 가져옴
        - sort 기능
    - <SearchList> : 태그 검색 리스트 조회
- <Login>


## Spring Security & JWT를 활용한 REST API Secure 설정
> JWT 토근을 검증하기 위해 사용자 정의 JWT 토큰 기반 인증 필터를 작성

JWT 토큰 유효성 검증을 위해 JwtTokenFilter 생성 
```JAVA
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provier 주입
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
```

JwtTokenProvider 클래스를 생성하여 JWT 토큰 생성, 토큰 검증, 인증 정보 조회 등 기능을 추가

```JAVA
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효

    @Autowired
    private AccountService accountService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String username, String roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidMilisecond);
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = accountService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "Bearer: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}
```

JwtTokenFilter를 설정하기 위한 JwtConfigurer 클래스를 생성

```JAVA
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
```

WebSecurityConfig에 JwtConfigurer 적용

```JAVA
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Spring Security에서 권장하는 패스워드 인코더 방식 적용, 자세한건 Spring Security Docs 참조 (목차 10.10.2 DelegatingPasswordEncoder)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Spring Security에서 세션을 만들거나 사용하지 않음
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/accounts/**").permitAll() // '/api/accounts/**' 관련 요청만 인증 없이 조회 가능
                    //.antMatchers("/api/secure/**").hasRole("USER") // 기본적으로 ROLE_* < 로 검색한다. 따라서 ROLE_ 을 붙여서 권한을 등록해줘야함
                    .anyRequest().authenticated() // '/api/accounts/**' 을 제외한 모든 요청에 인증이 필요함
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider)); // Jwt 인증 방식 적용
    }
}
```

로그인 요청시 JWT 토큰 생성 관련 로직
아이디 / 비밀번호가 일치하면 토큰을 생성해서 반환한다.

AccountController.java
```JAVA
@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // ... 코드 생략

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginMember(@RequestBody Account account, BindingResult bindingResult) throws Exception {
        try {
            String userEmail = account.getEmail();
            String password = account.getAccountPwd();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            Account getAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new InvalidAccountException("ID / PW 입력 정보를 다시 확인해주세요."));
            String token = jwtTokenProvider.createToken(getAccount.getEmail(), getAccount.getAuthorities().toString());

            return ok(token);
        } catch (AuthenticationException e) {
            throw new InvalidAccountException("ID / PW 입력 정보를 다시 확인해주세요.");
        }
    }

    // ... 코드 생략
}
```

Front Login 페이지에서 로그인을 성공적으로 했을 때, JWT Token 값을 리턴 받게 되고, 해당 Token 값을 LocalStorage에 저장한다. 그 후 LocalStorage에 있는 토큰 값을 `/api/accounts/me` 로 요청하여 해당 토큰이 유효하면 로그인 된 상태로 설정하고, 실패 시 기존에 있는 토큰 값을 폐기하고 다시 로그인을 통해 Jwt Token 값을 받아야한다.  

FrontEnd store.js
```javascript
// 생략 ...
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 생략 ...
  },
  getters: {
    // 생략 ...
  },
  mutations: {
    // 생략 ...
  },
  actions: {
    loginProcess ({ commit, dispatch }, { email, password }) {
      return new Promise((resolve, reject) => {
        api.loginAccount(email, password)
          .then(response => {
            if (response.status === 200) {
              localStorage.setItem('token', response.data)

              dispatch('getMemberInfo')
            }
            resolve(response)
          })
          .catch(error => {
            let message = error.response.data.message

            commit('login_error', {
              userEmail: email
            })
            commit('alertSetting', {
              message: message
            })
            setTimeout(() => {
              commit('alertInit')
            }, 5000)
            // eslint-disable-next-line prefer-promise-reject-errors
            reject(message)
          })
      })
    },
    // 생략 ...
    getMemberInfo ({ commit }) {
      let token = localStorage.getItem('token')

      if (token == null) { return }

      let config = {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }

      api.getAccountInfo(config).then(response => {
        if (response.status === 200) {
          let userObj = response.data

          commit('login_success', {
            userName: userObj.username,
            userEmail: userObj.email
          })
        } else {
          localStorage.clear()
        }
      })
      .catch(error => {
        console.log('Error: ' + error)
        localStorage.clear()
      })
    },
    logoutProcess ({ commit }) {
      commit('logout')
      localStorage.clear()
    }
  }
})
```

위와 같이 구현한 이유는 Vuex로 로그인 상태를 관리하는데, SPA 특성상 새로고침을 하게 될 경우 로그인 정보를 잃게 된다. 따라서, JWT Token 값을 LoacalStorage에 보관해서 페이지 새로고침이 발생할 경우 페이지가 생성되기전에 `getMemberInfo(JWT Token 값으로 인증 확인)` 메서드를 호출하여 회원 상태를 확인한다.  

FrontEnd Main.js
```javascript
new Vue({
  components: {
    'carousel': VueCarousel.Carousel,
    'slide': VueCarousel.Slide,
    'scroll-loader': ScrollLoader
  },
  router,
  store,
  beforeCreate () {
    this.$store.dispatch('getMemberInfo')
  },
  render: h => h(App)
}).$mount('#app')
```

`/api/accounts/me` 요청이 들어올 경우 JWT 토큰 유효성 확인 

AccountController.java
```JAVA
@GetMapping(path = "/me")
    public ResponseEntity<AccountInfo> getAccountInfo(@AuthenticationPrincipal Account account){
        return ok(new AccountInfo(account.getAccountId(), account.getEmail()));
    }
```

## REST API 예외 처리
> 각각의 예외가 들어올 경우 해당 에러에 맞는 응답 상태 및 에러메세지를 전송

RestApiError 클래스를 통해 JSON 형태로 에러 코드 및 메세지를 담아 응답한다.

RestApiError.java
```JAVA
public class RestApiError {

    @JsonFormat
    private HttpStatus status;

    @JsonFormat
    private String message;

    RestApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
```

RestExceptionHandler.java
```JAVA
// 유효하지 않은 사용자 계정, 이미지 업로드, 제약 조건 에러 등등 에러가 들어왔을 떄 핸들링 ...

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidAccountException.class})
    public ResponseEntity invalidAccount(InvalidAccountException ex, WebRequest request) {
        log.error("handling NotValidAccountException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity notFoundAccount(InvalidAccountException ex, WebRequest request) {
        log.error("handling InvalidAccountException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity constraintViolation(ConstraintViolationException ex, WebRequest request){
        String message = ex.getConstraintName().substring(ex.getConstraintName().lastIndexOf('_')+1) +"이 이미 사용중입니다.";
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(value = {InvalidImageException.class})
    public ResponseEntity invalidImage(InvalidImageException ex, WebRequest request){
        log.error("handling InvalidImageException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request){
        log.error("handling InvalidJwtAuthenticationException...");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestApiError(HttpStatus.UNAUTHORIZED, "로그인 세션이 만료되었습니다. 다시 로그인 해주세요."));
    }

}
```
