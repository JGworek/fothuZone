package zone.fothu.utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Put on any field to make lombok build a custom updater.
 * <p>
 * Even though it is not listed, this annotation also has the {@code onParam}
 * and {@code onMethod} parameter. See the full documentation for more details.
 * <p>
 * Example:
 * 
 * <pre>
 * private &#64;Updater int foo;
 * </pre>
 * 
 * will generate:
 * 
 * <pre>
 * public void updateFoo(int foo) {
 * 	this.foo = foo;
 * 	return this;
 * }
 * </pre>
 * 
 * <p>
 * This annotation can also be applied to a class, in which case it'll be as if
 * all non-static fields that don't already have a {@code Updater} annotation
 * have the annotation.
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
public @interface Updater {
	/**
	 * If you want your Updater to be non-public, you can specify an alternate
	 * access level here.
	 * 
	 * @return The updater method will be generated with this access modifier.
	 */
	lombok.AccessLevel value() default lombok.AccessLevel.PUBLIC;

	/**
	 * Any annotations listed here are put on the generated method. The syntax for
	 * this feature depends on JDK version (nothing we can do about that; it's to
	 * work around javac bugs).<br>
	 * up to JDK7:<br>
	 * {@code @Updater(onMethod=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 * {@code @Updater(onMethod_={@AnnotationsGohere})} // note the underscore after
	 * {@code onMethod}.
	 * 
	 * @return List of annotations to apply to the generated Updater method.
	 */
	AnyAnnotation[] onMethod() default {};

	/**
	 * Any annotations listed here are put on the generated method's parameter. The
	 * syntax for this feature depends on JDK version (nothing we can do about that;
	 * it's to work around javac bugs).<br>
	 * up to JDK7:<br>
	 * {@code @Updater(onParam=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 * {@code @Updater(onParam_={@AnnotationsGohere})} // note the underscore after
	 * {@code onParam}.
	 * 
	 * @return List of annotations to apply to the generated parameter in the
	 *         Updater method.
	 */
	AnyAnnotation[] onParam() default {};

	/**
	 * Placeholder annotation to enable the placement of annotations on the
	 * generated code.
	 * 
	 * @deprecated Don't use this annotation, ever - Read the documentation.
	 */
	@Deprecated
	@Retention(RetentionPolicy.SOURCE)
	@Target({})
	@interface AnyAnnotation {
	}
}