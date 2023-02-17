package apicore.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention( RetentionPolicy.RUNTIME )
public @interface AccessType {
	  static final String PUBLIC = "PUBLIC";
	  static final String PRIVATE_JWT = "PRIVATE_JWT";
	  static final String PRIVATE_JWT_FORCE_CHECKING = "PRIVATE_JWT_FORCE_CHECKING";
	  String value();
}