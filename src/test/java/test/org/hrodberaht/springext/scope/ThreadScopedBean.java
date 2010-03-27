package test.org.alex.springext.scope;

import org.hrodberaht.springext.scope.ScopeType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-26
 * Time: 00:17:23
 * To change this template use File | Settings | File Templates.
 */


@Component
@Scope(value = ScopeType.THREAD)
public class ThreadScopedBean {
}
