import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet.AuthFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthFilterTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    FilterChain chain;

    private AuthFilter filter = new AuthFilter();

    @Test
    public void testFilterRedirectsWhenNotLogged() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/app");

        filter.doFilter(request, response, chain);

        verify(response).sendRedirect("/app/login");
        verify(chain, never()).doFilter(request, response);
    }
}