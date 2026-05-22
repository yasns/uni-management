package lt.vu.mif.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@TimedOperation
public class TimingInterceptor {

    @AroundInvoke
    public Object measure(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return ctx.proceed();
        } finally {
            System.out.printf("[TIMING] %s.%s: took %dms%n",
                    ctx.getTarget().getClass().getSimpleName(),
                    ctx.getMethod().getName(),
                    System.currentTimeMillis() - start);
        }
    }
}
