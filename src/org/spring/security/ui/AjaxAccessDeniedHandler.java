package org.spring.security.ui;

import java.util.Map;

import org.spring.security.model.SecurityUtil;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.GenericInitiator;


/**
 * @author Hawk
 *
 */
public class AjaxAccessDeniedHandler extends GenericInitiator {

        public void doInit(Page page, Map<String, Object> args) throws Exception {
                // when this initiator has been executed that means users encounter access denied problem.
                
                Execution exec = Executions.getCurrent();
                
                if (null == SecurityUtil.getUser()){ //unauthenticated user
                        exec.sendRedirect("/index.zul");
                }else{
                        //display error's detail
                        Executions.createComponents("/WEB-INF/errors/displayAccessDeniedException.zul", null, args);
                }
        }
}