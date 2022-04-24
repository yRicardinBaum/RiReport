package rireport.ridev.com.br.Starters;

import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import rireport.ridev.com.br.Data.TypesData;
import rireport.ridev.com.br.Libs.ModuleLogger;
import rireport.ridev.com.br.Puniments.Puniment;

import java.util.logging.Level;

@Data(staticConstructor = "of")
public class TypesStarter {
    private final ConfigurationSection sec;
    public static final ModuleLogger LOGGER = new ModuleLogger("Rireport Types Starter");
    public void init() {
        try {
            for (String s : sec.getKeys(false)) {
                String nome = sec.getString(s + ".nome");

                if (nome != null) {
                    Puniment pn = new Puniment();
                    pn.setReason(nome);
                    TypesData.getPuniments().add(pn);
                } else {
                    LOGGER.log(Level.SEVERE, "O tipo de report " + s + " não teve o valor 'nome' bem configurado, certifique-se de que é um valor de string e que não seja nulo!");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar carregar os tipos de reports", e);
        } finally {
            LOGGER.log(Level.FINE, TypesData.getPuniments().size() + " types carregados com sucesso!");
        }
    }
    }
