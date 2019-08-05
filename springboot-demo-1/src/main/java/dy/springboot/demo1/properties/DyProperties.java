package dy.springboot.demo1.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/23
 */
@Component
@ConfigurationProperties(prefix = "dy")
//@PropertySource(value = "classpath:dy.yml", ignoreResourceNotFound = true, factory = YmlPropertiesFactory.class)
public class DyProperties {

    private String name;

    private String telephone;

    private Map<String, String> map;

    private DyProperties.Game game;

    private List<Game> listGame;

    /* inner Class */
    public static class Game {
        private String gameName;
        private String gameStation;

        @Override
        public String toString() {
            return "Game{" +
                    "gameName='" + gameName + '\'' +
                    ", gameStation='" + gameStation + '\'' +
                    '}';
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getGameStation() {
            return gameStation;
        }

        public void setGameStation(String gameStation) {
            this.gameStation = gameStation;
        }
    }


    /* getter and setter */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Game> getListGame() {
        return listGame;
    }

    public void setListGame(List<Game> listGame) {
        this.listGame = listGame;
    }
}
