package controllers.toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.EmployeeView;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;

        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){
        }

        //既存のEmployeeViewデータを削除
        em.getTransaction().begin();
        em.createNamedQuery("deleteAllEmployeeViews")
            .executeUpdate();
        em.getTransaction().commit();

        //セッションスコープからuser情報を取得
        Employee user = (Employee)request.getSession().getAttribute("login_employee");


        //user_idがフォローしている社員情報を取得
        List<Employee> follows = em.createNamedQuery("getFollows", Employee.class)
                                    .setParameter("user", user)
                                    .getResultList();


        //Employeeテーブルの情報をEmployeeViewに保存、follow_flagの変更
        List<Integer> ids = em.createNamedQuery("getAllEmployee_ids", Integer.class)
                .getResultList();
        List<String> codes = em.createNamedQuery("getAllEmployee_codes", String.class)
                .getResultList();
        List<String> names = em.createNamedQuery("getAllEmployee_names", String.class)
                .getResultList();
        List<Integer> delete_flags = em.createNamedQuery("getAllEmployee_delete_flags", Integer.class)
                .getResultList();
        long resultSize = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                .getSingleResult();

        for(int i = 0; i < resultSize; i++){
            em.getTransaction().begin();
            //Employee情報の取得・保存
            EmployeeView ev = new EmployeeView();
            Integer id = ids.get(i);
            String code = codes.get(i);
            String name = names.get(i);
            Integer delete_flag = delete_flags.get(i);

            ev.setEmployee_id(id);
            ev.setEmployee_code(code);
            ev.setEmployee_name(name);
            ev.setEmployee_delete_flag(delete_flag);
            ev.setFollow_flag(0);
            //フォローしている社員の判定
            if(follows.size() > 0){
                for(int j = 0; j < follows.size(); j++){
                    if(id.equals(follows.get(j).getId())){
                        ev.setFollow_flag(1);
                    }
                }
            }
            em.persist(ev);
            em.getTransaction().commit();

        }


        List<EmployeeView> employeeViews = em.createNamedQuery("getAllEmployeeViews", EmployeeView.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                .getSingleResult();

        em.close();


        request.setAttribute("employeesViews", employeeViews);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }

}
