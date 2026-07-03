package ar.com.grupoesfera.repartir.steps.grupos;

import ar.com.grupoesfera.repartir.exceptions.MontoNegativoException;
import ar.com.grupoesfera.repartir.model.Gasto;
import ar.com.grupoesfera.repartir.model.Grupo;
import ar.com.grupoesfera.repartir.services.MontosService;
import ar.com.grupoesfera.repartir.steps.FastCucumberSteps;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NoSePuedeAgregarSaldoNegativo extends FastCucumberSteps {

    private Grupo grupo;
    private final MontosService montos = new MontosService();
    private RuntimeException excepcion;

    @Dado("que existe un grupo con saldo en {int}")
    public void queHayUnGrupoConSaldoTotalIgualA(int monto) {
        grupo = new Grupo();
        grupo.setTotal(BigDecimal.valueOf(monto));
    }

    @Cuando("uno de sus usuarios intenta sumar un monto de {int}")
    public void elUsuarioIntentaAgregarUnSaldoDe(int monto) {
        try {
            Gasto gasto = new Gasto();
            gasto.setMonto(BigDecimal.valueOf(monto));
            montos.acumularAlTotal(grupo, gasto);
        } catch (MontoNegativoException e) {
            excepcion = e;
        }
    }

    @Entonces("no se suma el saldo al grupo")
    public void noSeAgregaElSaldo() {
        assertThat(excepcion)
                .isNotNull()
                .isInstanceOf(MontoNegativoException.class);
    }
    @Y("el total debe permanecer en {int}")
    public void elTotalPermaneceEnCero(int saldo) {
        assertThat(grupo.getTotal()).isEqualTo(BigDecimal.valueOf(saldo));
    }
}
