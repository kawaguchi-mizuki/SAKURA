package cats.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cats.dto.LoginInfoDto;
import cats.param.SessionConst;

@Component
public class LoginFilter implements Filter {
	//	チェック除外画面"/sbadmin/.+","/favicon.ico",
		private String excludeDispList[] =
			{
				"/","/Login","/auth","/Logout","/css/.+","/img/.+","/error/.*","/User/Entry","/User/Check","/User/GetCourseList","/GetCourseList"
				,"/check","/TwoCheck","/TwoConf"
			};

		@Autowired
		HttpSession session;

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			//	リクエストのサーブレットパスを取得
			String servletPath = ((HttpServletRequest)request).getServletPath();
			//	除外対象かどうかをチェック
			if( ExcludeCheck(servletPath )) {
				chain.doFilter(request, response);
				return;
			}

			//	セッションからユーザー情報を取得
			LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

			if( loginInfo == null ) {
				//	ログイン画面へ転送
				((HttpServletResponse)response).sendRedirect( "/Login");
				return;
			}

			chain.doFilter(request, response);

		}
		/**
		 * 除外URLかどうかの判定を行う
		 *
		 * @param servletPath
		 * @return
		 */
		private boolean ExcludeCheck(String servletPath) {
			boolean ret = false;

			for( String exclude :excludeDispList ) {
				if(servletPath.matches(exclude)) {
					ret = true;
					break;
				}
			}

			return ret;
		}
}
