package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto toinenVarasto;
    Varasto negatiivinenVarasto;
    Varasto parametrillinenVarasto;
    Varasto parametrillinenVarastoPositiivinen;
    Varasto parameterillinenVarastoNegatiivinen;
    double vertailuTarkkuus = 0.0001;
    private Varasto saldoIsompiKuinTilavuusVarasto;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        toinenVarasto = new Varasto(0);
        negatiivinenVarasto = new Varasto(-1);
        parametrillinenVarasto = new Varasto(0, 0);
        parametrillinenVarastoPositiivinen = new Varasto(10, 10);
        parameterillinenVarastoNegatiivinen = new Varasto(-10, -10);
        saldoIsompiKuinTilavuusVarasto = new Varasto(5, 6);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, toinenVarasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, negatiivinenVarasto.getSaldo(), vertailuTarkkuus);
    }
    @Test
    public void konstruktoriLuoVarastonOikeillaParametreilla() {
        assertEquals(0, parametrillinenVarasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, parametrillinenVarasto.getTilavuus(), vertailuTarkkuus);

        assertEquals(10, parametrillinenVarastoPositiivinen.getSaldo(), vertailuTarkkuus);
        assertEquals(10, parametrillinenVarastoPositiivinen.getTilavuus(), vertailuTarkkuus);

        assertEquals(0, parameterillinenVarastoNegatiivinen.getSaldo(), vertailuTarkkuus);
        assertEquals(0, parameterillinenVarastoNegatiivinen.getTilavuus(), vertailuTarkkuus);

        assertEquals(5, saldoIsompiKuinTilavuusVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    @Test
    public void negatiivinenMaaraEiLisaaSaldoa() {
        varasto.lisaaVarastoon(-10);
        assertNotEquals(-10, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        varasto.lisaaVarastoon(10);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
        varasto.lisaaVarastoon(-10);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }
    @Test
    public void maaraEiYlitaTilavuutta() {
        varasto.lisaaVarastoon(11);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }
    @Test
    public void negatiivinenOttaminenPalauttaaOikein() {
        double otettuMaara = varasto.otaVarastosta(-10);
        assertEquals(0, otettuMaara, vertailuTarkkuus);
        varasto.lisaaVarastoon(10);
        otettuMaara = varasto.otaVarastosta(-10);
        assertEquals(0, otettuMaara, vertailuTarkkuus);
    }
    @Test
    public void otetaanKaikkiJosYliMaara() {
        varasto.lisaaVarastoon(10);
        double otettu = varasto.otaVarastosta(11);
        assertEquals(10, otettu, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(1);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void merkkijonoPalauttaaOikein() {
        assertEquals("saldo = 0.0, vielä tilaa 10.0", varasto.toString());
        assertEquals("saldo = 10.0, vielä tilaa 0.0", parametrillinenVarastoPositiivinen.toString());
    }

}