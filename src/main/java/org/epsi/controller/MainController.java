package org.epsi.controller;

import org.epsi.configuration.Database;
import org.epsi.model.Document;
import org.epsi.model.Module;
import org.epsi.model.Room;
import org.epsi.model.User;
import org.epsi.model.quizz.Survey;
import org.epsi.model.quizz.UserSurvey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {


    private static String d(Document document) {
        return " <p class=\"list-group-item\">" + document.getPath() + "\n" +
                "                        <a href=\"/documents/" + document.getPath() + "\">\n" +
                "                            <span class=\"pull-right glyphicon glyphicon-download\"></span></a></p>";
    }

    private static String q(Survey s, User user) {

        UserSurvey userSurvey = user.getUserSurveys().stream().filter(u -> u.getSurvey().getId() == s.getId()).findAny().orElse(null);

        if(userSurvey != null && userSurvey.getResult() == 1) {
            return " <p class=\"list-group-item\">" + s.getName() + " par " + s.getOwner().getUsername() + "\n" +
                    "                            <span style='color : green;' class=\"pull-right glyphicon glyphicon-ok\"></span></p>";
        } else if(userSurvey != null && userSurvey.getResult() == 0) {
            return " <p class=\"list-group-item\">" + s.getName() + " par " + s.getOwner().getUsername() + "\n" +
                    "                            <span style='color : red;' class=\"pull-right glyphicon glyphicon-remove\"></span></p>";
        }


        return " <p class=\"list-group-item\">" + s.getName() + " par " + s.getOwner().getUsername() + "\n" +
                "                        <a href=\"/survey/?id=" + s.getId() + "\">\n" +
                "                            <span class=\"pull-right glyphicon glyphicon-play-circle\"></span></a></p>";
    }

    @RequestMapping("/")
    public String refresh(Model model) {
        return "splash";
    }

    @RequestMapping("/home/")
    public String refresh(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        model.addAttribute("rooms", Room.rooms.values());
        model.addAttribute("user", user);

        if (user == null) {
            return "home";
        } else {
            return "index";
        }

    }

    @RequestMapping("/login/")
    public String login(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "login";
        } else {
            return "redirect:/home/";

        }

    }

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public String connect(Model model, HttpServletResponse response, HttpSession session,
                          @RequestParam("name") String name,
                          @RequestParam("password") String password) {

        System.err.println("Name = " + name);
        System.err.println("Password = " + password);

        User user = User.users.values().stream().filter(u -> u.getUsername().equalsIgnoreCase(name)).findAny().orElse(null);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Nom de compte ou mot de passe incorrect");
            return "login";
        }

        session.setAttribute("user", user);

        return "redirect:/home/";
    }

    @RequestMapping("/register/")
    public String register(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "register";
        } else {
            return "redirect:/home/";

        }

    }

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public String registerValidate(Model model, HttpServletResponse response, HttpSession session, @RequestParam("email") String email,
                                   @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("surname") String surname,
                                   @RequestParam("password") String password, @RequestParam("password2") String password2) {

        if (!password.equals(password2)) {
            model.addAttribute("error", "Les mots de passes ne sont pas identiques");
            return "register";
        }

        User user = User.users.values().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);

        if (user != null) {
            model.addAttribute("error", "Nom d'utilisateur déjà utilisé");
            return "register";
        }

        user = User.users.values().stream().filter(u -> u.getEmail().equals(email)).findAny().orElse(null);

        if (user != null) {
            model.addAttribute("error", "Email déjà utilisé");
            return "register";
        }

        try {
            PreparedStatement p = Database.database.connection.prepareStatement("INSERT INTO users(name,username, surname, email, password) " +
                    "VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            p.setString(1, name);
            p.setString(2, username);
            p.setString(3, surname);
            p.setString(4, email);
            p.setString(5, password);

            p.execute();

            ResultSet resultSet = p.getGeneratedKeys();
            int id = 0;
            if (resultSet.next())
                id = resultSet.getInt(1);

            System.err.println("Generated id " + id);

            new User(id, email, password, username, surname, name);

            session.setAttribute("user", user);

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "Une erreur inconnue s'est prdouite");
            return "register";
        }


        return "redirect:/home/";
    }

    @RequestMapping(value = "/module/", method = RequestMethod.GET)
    public String module(Model model, HttpServletResponse response, HttpSession session,
                         @RequestParam("id") int id) {

        System.err.println(id);

        Room room = Room.rooms.get(id);

        List<Module> modules = room.getModules();

        model.addAttribute("room", room);


        for (Module module : modules) {
            module.documents = Math.toIntExact(Document.documents.values().stream().filter(d -> d.getModule() == module.getId()).count());
            module.surveys = Math.toIntExact(Survey.surveys.values().stream().filter(d -> d.getModule() == module.getId()).count());
        }

        model.addAttribute("modules", modules);

        return "module";
    }

    @RequestMapping(value = "/vmodule/", method = RequestMethod.GET)
    public String vmodule(Model model, HttpServletResponse response, HttpSession session,
                          @RequestParam("id") int id) {

        Module module = Module.modules.get(id);

        model.addAttribute("module", module);

        Document.documents.values().forEach(d -> d.setHtml(d(d)));
        Survey.surveys.values().forEach(d -> d.setHtml(q(d, (User) session.getAttribute("user"))));

        model.addAttribute("documents", Document.documents.values().stream().filter(d ->
                d.getModule() == module.getId()).collect(Collectors.toList()));

        model.addAttribute("surveys", Survey.surveys.values().stream().filter(d ->
                d.getModule() == module.getId()).collect(Collectors.toList()));

        session.setAttribute("last_module", id);

        return "vmodule";
    }

    @RequestMapping(value = "/survey/", method = RequestMethod.GET)
    public String survey(Model model, HttpServletResponse response, HttpSession session,
                         @RequestParam("id") int id) {

        Survey survey = Survey.surveys.get(id);

        session.setAttribute("last_survey", survey.getId());

        model.addAttribute("survey", survey);
        model.addAttribute("questions", survey.getAnswers());


        return "survey";
    }

    @RequestMapping(value = "/check/", method = RequestMethod.GET)
    public String surveyCheck(Model model, HttpServletResponse response, HttpSession session,
                              @RequestParam("value") int value) {

        Survey survey = Survey.surveys.get((int) session.getAttribute("last_survey"));

        Database.database.query("INSERT INTO documents(path, user, module) VALUES (?,?,?)");
        User user = (User) session.getAttribute("user");

        try {
            PreparedStatement p = Database.database.connection.prepareStatement("INSERT INTO user_survey(user, survey, result) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);

            p.setInt(1, user.getId());
            p.setInt(2, survey.getId());
            p.setInt(3, value == survey.getValid() ? 1 : 0);

            p.execute();

            user.getUserSurveys().add(new UserSurvey(user, survey, value == survey.getValid() ? 1 : 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/vmodule/?id=" + session.getAttribute("last_module");
    }


}
