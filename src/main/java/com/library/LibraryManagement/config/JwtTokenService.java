package com.library.LibraryManagement.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.library.LibraryManagement.domain.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtTokenService implements Serializable {
	
    private final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final String ROLES = "ROLES";
    public static final String USERID = "USERID";
    public static final String DEFAULT_LANG = "langId";
    public static final long EXTERNAL_USER_ROLE_ID = 184;
    public static final long EXTERNAL_ROLE_CATERGORY = 10122;

    @Value("${jwt.secret}")
    private String secret;
    
    @Autowired
    Environment env;
    
   /* @Autowired
    UserRepository userRepo;
    @Autowired
    UserSessionHistoryRepository sessionHistoryRepo;
    @Autowired
    private IUserService userService;*/

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<String> getRoles(String token) {
       return getClaimFromToken(token, claims -> (List) claims.get(ROLES));
    }
    
    public Integer getUserIdFromToken(String token) {
    	return getClaimFromToken(token, claims -> (Integer) claims.get(USERID));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public JwtResponse generateToken(Authentication authentication,HttpSession session,String langId) {
    	logger.info("Inside new token generation for langId : "+langId);
        final Map<String, Object> claims = new HashMap<>();
        /*final User user = (User) authentication.getPrincipal();
       	Optional<AUserHdr> userHdr = userRepo.findById(user.getUserId());
        final List<String> previliges = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(Collectors.toList());
        claims.put(ROLES, previliges);
        claims.put(USERID, user.getUserId());
        claims.put(DEFAULT_LANG,langId);
        AUserSessionHistory history = new AUserSessionHistory();
        history.setSessionId(session.getId());
        history.setUserId(user.getUserId());
        Date creationTime = new Date(session.getCreationTime());
        history.setSessionInTime(creationTime);
        sessionHistoryRepo.saveAndFlush(history);
        String firstName="";
        String lastName="";
        if (userHdr.get().getAUserLangs()!=null && userHdr.get().getAUserLangs().size()>0 ) {
        	List<AUserLang> userLangList=userHdr.get().getAUserLangs().stream().filter((u)->u.getLangId()==Long.valueOf(langId)).collect(Collectors.toList());
        	if(userLangList!=null && userLangList.size()>0) {
        		firstName=userLangList.get(0).getUserFirstName();
        		lastName=userLangList.get(0).getUserLastName();
        	}
        }*/
        //logger.info("session history details saved for userId : "+user.getUserId()+ ", User Name :"+ user.getUsername());
        //return new JwtResponse(generateToken(claims, user.getUsername()), user.getUsername(),langId, previliges,String.valueOf(user.getUserId()),userHdr.get().getPasswordCategory(), user.getUserId(),firstName,lastName);
        /*final List<String> previliges = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(ROLES, previliges);*/
        return new JwtResponse(generateToken(claims,env.getProperty("spring.security.user.username")));
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String generateToken(Map<String, Object> claims, String subject) {
        final long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS384, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return username != null && !isTokenExpired(token);
    }
    
    /*private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }*/
    
	/*public void validateUserIsInteralOrExternal(Authentication authentication) {
		ARbacRolesHdr roleHdr = null;
        final User user = (User) authentication.getPrincipal();
       	Optional<AUserHdr> userHdr = userRepo.findById(user.getUserId());
       	logger.info("validateUserIsInteralOrExternal === > for the User ID :"+ user.getUserId());
       	if(userHdr.isPresent()) {
       		roleHdr = userHdr.get().getRoles().stream()
       				.filter(roles -> roles.getRoleId() == EXTERNAL_USER_ROLE_ID && roles.getUserCategoryID() == EXTERNAL_ROLE_CATERGORY).findAny().orElse(null);
       	}	
	}*/
	
	 //generate token for user
    /*public JwtResponse generateNewToken(String token,HttpSession session,String langId) {
    	AUserHdr user = null;
    	final String username = getUsernameFromToken(token);
    	logger.info("generateNewToken- Existing token : "+ token + " , user :"+ username);
    	if(username != null) {
    		user = userService.findUserByLoginId(username);
    	}
		 String firstName="";
	     String lastName="";
	     if (user!=null && user.getAUserLangs()!=null && user.getAUserLangs().size()>0 ) {
	     	List<AUserLang> userLangList=user.getAUserLangs().stream().filter((u)->u.getLangId()==Long.valueOf(langId)).collect(Collectors.toList());
	     	if(userLangList!=null && userLangList.size()>0) {
	     		firstName=userLangList.get(0).getUserFirstName();
	     		lastName=userLangList.get(0).getUserLastName();
	     	}
	     }
        final List<String> roles = getRoles(token);
        final Map<String, Object> claims = new HashMap<>();
        claims.put(ROLES, roles);
        claims.put(USERID, user.getId());
        claims.put(DEFAULT_LANG,langId);
        AUserSessionHistory history = new AUserSessionHistory();
        history.setSessionId(session.getId());
        history.setUserId(user.getId());
        Date creationTime = new Date(session.getCreationTime());
        history.setSessionInTime(creationTime);
        sessionHistoryRepo.saveAndFlush(history);
        return new JwtResponse(generateToken(claims, username), username,langId, roles,String.valueOf(user.getId()),user.getPasswordCategory(), user.getId(),firstName,lastName);
    }*/
	
	
}