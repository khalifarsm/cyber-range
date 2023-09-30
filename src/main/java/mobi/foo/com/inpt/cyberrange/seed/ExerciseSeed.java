package mobi.foo.com.inpt.cyberrange.seed;

import mobi.foo.com.inpt.cyberrange.domain.Difficulty;
import mobi.foo.com.inpt.cyberrange.domain.Exercise;
import mobi.foo.com.inpt.cyberrange.repository.CategoryRepository;
import mobi.foo.com.inpt.cyberrange.repository.ExerciseRepository;
import mobi.foo.com.inpt.cyberrange.repository.QuestionRepository;
import mobi.foo.com.inpt.cyberrange.repository.UserRepository;
import mobi.foo.com.inpt.cyberrange.services.MachineService;
import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.domain.Category;
import mobi.foo.com.inpt.cyberrange.domain.Question;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ExerciseSeed {
    private final ExerciseRepository exerciseRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final MachineService machineService;
    private final CategoryRepository categoryRepository;

    //@PostConstruct
    public void create() {
        Category hackCategory = new Category();
        hackCategory.setName("Ethical Hacking");
        categoryRepository.save(hackCategory);

        Category forensicCategory = new Category();
        forensicCategory.setName("Forensics");
        categoryRepository.save(forensicCategory);

        Category webCategory = new Category();
        webCategory.setName("Web");
        categoryRepository.save(webCategory);


        Exercise exercise = new Exercise()
                .setCategory(hackCategory)
                .setDescription("Cet exercice vise à gagner l’accès à d’une machine virtuelle en exploitant une ou plusieurs vulnérabilités, cette machine est appelée Metaspoitable.\n" +
                        "Metasploitable est une machine virtuelle Linux intentionnellement vulnérable qui peut être utilisée pour dispenser une formation à la sécurité, tester des outils de sécurité et pratiquer des techniques de test d'intrusion courantes. La machine virtuelle fonctionne sur tous les produits VMware récents et d'autres technologies de visualisation telles que VirtualBox.")
                .setDifficulty(Difficulty.EASY)
                .setLink("/vnc_auto.html")
                .setImage("")
                .setName("Metaspoitable")
                .setProhibited("L’utilisateur ne doit pas utiliser ou télécharger des outils de scan autre que ceux mise à sa disposition.")
                .setScript("scripts/metaspoitable.yml")
                .setSolution("")
                .setTimeToClose(60 * 60 * 1000)
                .setTimeToComplete("1h 00min")
                .setTodo("Une machine kali est mise à la disposition de l’utilisateur disposant de NMAP et metaspoit installé.\n" +
                        "L’utilisateur doit utiliser les outils mises à sa disposition pour accéder à la machine cible en exploitant une des vulnérabilités de la machine.\n" +
                        "La machine cible est enregistrée sous le nom de domaine victim, alors une commande \n" +
                        "ping victim\n" +
                        "Permettra à l’utilisateur de connaitre l’adresse IP de la cible.\n" +
                        "Après avoir accéder à la machine cible l’utilisateur doit lire le contenu du fichier flag.txt qui se trouve dans le répertoire root et le copier dans la section réponse de l’exercice.\n" +
                        "L’utilisateur dispose d’une heure pour compléter l’exercice sinon ce dernier s’arrête automatiquement.\n");
        exerciseRepository.save(exercise);

        questionRepository.save(new Question()
                .setExercise(exercise)
                .setQuestion("Donnez le contenu du fichier /root/flag.txt?")
                .setPoints(100)
                .setAnswer("inpt-pfe-metaspoitable-flag"));

        Exercise exercise2 = new Exercise()
                .setCategory(forensicCategory)
                .setDescription("Cette exercice concerne l’analyse de mémoire en utilisant l’outils volatility.\n" +
                        "La criminalistique de la mémoire (parfois appelée analyse de la mémoire) fait référence à l'analyse des données volatiles dans le dump de la mémoire d'un ordinateur. Les professionnels de la sécurité de l'information effectuent des analyses de la mémoire pour enquêter et identifier les attaques ou les comportements malveillants qui ne laissent pas de traces facilement détectables sur les données du disque dur.\n" +
                        "Volatility est un framework open source pour l'informatique légale et en particulier le recouvrement de mémoire, utilisé dans la réponse à incident informatique et l'analyse des logiciels malveillants. Le logiciel est écrit en Python et prend en charge les systèmes d'exploitation Microsoft Windows, Mac OS.\n")
                .setDifficulty(Difficulty.EASY)
                .setImage("")
                .setName("Volatility")
                .setProhibited("Il est interdit d’utiliser des outils autres que ceux mise à votre disposition dans la machine.")
                .setScript("scripts/volatility.yml")
                .setSolution("")
                .setTimeToClose(60 * 60 * 1000)
                .setTimeToComplete("1h 00min")
                .setTodo("Cette exercice simule la mémoire d’un ordinateur windows affecter par un malware stocké dans le ficher malware.vmem dans le dossier root, le role de l’utilisateur est d’analyser la mémoire avec l’outils volatility et répondre aux questions suivantes :\n" +
                        "•\tDonnez le PID du processus malveillant ?\n" +
                        "•\tDonnez la taille en octets de l’exécutable du processus malveillant ?\n" +
                        "•\tDonnez la taille en octets de la mémoire utilisée par le processus malveillant ?\n" +
                        "Volatility est installé sur la machine dans le dossier /opt/volatility\n" +
                        "Pour simplifier l’utilisation de la commande vous pouvez créer un alias avec la commande suivante :\n" +
                        "Alias volatility=’’python2.7 /opt/volatility/vol.py’’\n");
        exerciseRepository.save(exercise2);

        questionRepository.save(new Question()
                .setExercise(exercise2)
                .setQuestion("Donnez le PID du processus malveillant ?")
                .setPoints(40)
                .setAnswer("1640"));

        questionRepository.save(new Question()
                .setExercise(exercise2)
                .setQuestion("Donnez la taille en octets de l’exécutable du processus malveillant ?")
                .setPoints(40)
                .setAnswer("29184"));

        questionRepository.save(new Question()
                .setExercise(exercise2)
                .setQuestion("Donnez la taille en octets de la mémoire utilisée par le processus malveillant ?")
                .setPoints(40)
                .setAnswer("77205504"));


        Exercise exercise3 = new Exercise()
                .setCategory(webCategory)
                .setDescription("Cette exercice vis à exploiter la vulnérabilité SQL Injection dans une API REST\n" +
                        "L'injection SQL est une forme de cyberattaque dans laquelle un attaquant utilise un morceau de code SQL (Structured Query Language) pour manipuler une base de données et accéder à des informations potentiellement importantes.\n" +
                        "Il s'agit de l'un des types d'attaques les plus répandus et les plus menaçants car il peut être utilisé pour compromettre n'importe quelle application Web ou n'importe quel site Web utilisant une base de données SQL.\n" +
                        "REST est un style d'architecture logicielle qui définit un ensemble de contraintes pour la création de services Web. Les services Web conformes au style architectural REST, également appelés services Web RESTful, établissent l'interopérabilité entre les ordinateurs sur Internet\n")
                .setDifficulty(Difficulty.EASY)
                .setLink("/vnc_auto.html")
                .setImage("")
                .setName("Sql injection")
                .setProhibited("Il est interdit d’utiliser des outils autres que ceux mise à votre disposition dans la machine.")
                .setScript("scripts/sql-injection.yml")
                .setSolution("")
                .setTimeToClose(60 * 60 * 1000)
                .setTimeToComplete("1h 00min")
                .setTodo("Vous devez utiliser votre machine linux pour exploiter la vulnérabilité dans l’api explosé dans le port 8080 sur la machine victim\n" +
                        "L’api dispose de deux endpoints permettant de lister des pays a savoir :\n" +
                        "GET : /countries\n" +
                        "Pour la liste des pays\n" +
                        "GET : /countries/{id} pour avoir les détails d’un pays spécifique. \n" +
                        "Votre rôle est de trouver la vulnérabilité dans l’application et de l’exploiter pour trouver le mot de passe de l’admin.\n");
        exerciseRepository.save(exercise3);

        questionRepository.save(new Question()
                .setExercise(exercise3)
                .setQuestion("le mot de passe de l'utilisateur admin ?")
                .setPoints(100)
                .setAnswer("inpt-admin-tag"));

//        User user = new User();
//        user.setEnabled(true);
//        user.setEmail("agent@shuttlefare.com");
//        userRepository.save(user);
//        Stack stack = machineService.run(exercise, user);
//        System.out.println(stack);
        //machineService.run
    }
}
