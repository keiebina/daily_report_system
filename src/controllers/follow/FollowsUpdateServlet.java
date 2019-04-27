package controllers.follow;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsUpdateServlet
 */
@WebServlet("/follows/update")
public class FollowsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            //セッションスコープからuser_id情報の取得 //リクエストパラメータからfollow_idの取得
            Employee user = (Employee)request.getSession().getAttribute("login_employee");
            Integer followId = Integer.parseInt(request.getParameter("follow_id"));
            Employee followEmployee = em.createNamedQuery("getEmployeesSearchById", Employee.class)
                                .setParameter("followId", followId)
                                .getSingleResult();

            //userのフォロー従業員をデータベースから取得
            List<Employee> follows = em.createNamedQuery("getFollows", Employee.class)
                                        .setParameter("user", user)
                                        .getResultList();

            //フォローしているユーザーかどうか
            if(FollowDecision.isFollowing(followId, follows)){

                //フォローしているユーザーだった場合
                em.getTransaction().begin();

                em.createNamedQuery("deleteFollow")
                    .setParameter("user", user)
                    .setParameter("followEmployee", followEmployee)
                    .executeUpdate();

                em.close();
                request.getSession().setAttribute("flush", "フォローを解除しました。");
                response.sendRedirect(request.getContextPath() + "/employees/index");


            }else{      //フォローしていないユーザーの場合

                //followテーブルにuser_id,follow_idを保存
                Follow f = new Follow();
                f.setUser(user);
                f.setFollowEmployee(followEmployee);

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute("flush", "フォローが完了しました。");
                response.sendRedirect(request.getContextPath() + "/employees/index");
            }

    }

}
