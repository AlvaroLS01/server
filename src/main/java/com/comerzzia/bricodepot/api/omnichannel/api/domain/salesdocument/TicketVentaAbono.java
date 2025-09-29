package com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Lightweight representation of a ticket used for printing invoices.
 * <p>
 * The original platform exposes a much richer domain model. For the purposes of the
 * API we only need a subset of that information in order to map the parameters expected
 * by the Jasper templates. The class intentionally keeps mutability to a minimum so that
 * every request operates on an isolated instance.
 */
public final class TicketVentaAbono {

    private final String documentUid;
    private final String codigoTicket;
    private final LocalDateTime fechaTicket;
    private final LocalDateTime fechaOrigen;
    private final String locatorId;
    private final String uidInstancia;
    private final Pais pais;
    private final boolean devolucion;
    private final String numeroPedido;
    private final byte[] logo;
    private final FiscalData fiscalData;
    private final List<LineaTicket> lineas;
    private final List<PromocionAplicada> promociones;
    private final List<Pago> pagos;
    private final Map<String, Object> atributosAdicionales;

    private TicketVentaAbono(Builder builder) {
        this.documentUid = builder.documentUid;
        this.codigoTicket = builder.codigoTicket;
        this.fechaTicket = builder.fechaTicket;
        this.fechaOrigen = builder.fechaOrigen;
        this.locatorId = builder.locatorId;
        this.uidInstancia = builder.uidInstancia;
        this.pais = builder.pais;
        this.devolucion = builder.devolucion;
        this.numeroPedido = builder.numeroPedido;
        this.logo = builder.logo;
        this.fiscalData = builder.fiscalData;
        this.lineas = Collections.unmodifiableList(new ArrayList<>(builder.lineas));
        this.promociones = Collections.unmodifiableList(new ArrayList<>(builder.promociones));
        this.pagos = Collections.unmodifiableList(new ArrayList<>(builder.pagos));
        this.atributosAdicionales = Collections.unmodifiableMap(builder.atributosAdicionales);
    }

    public String getDocumentUid() {
        return documentUid;
    }

    public String getCodigoTicket() {
        return codigoTicket;
    }

    public LocalDateTime getFechaTicket() {
        return fechaTicket;
    }

    public LocalDateTime getFechaOrigen() {
        return fechaOrigen;
    }

    public String getLocatorId() {
        return locatorId;
    }

    public String getUidInstancia() {
        return uidInstancia;
    }

    public Pais getPais() {
        return pais;
    }

    public boolean isDevolucion() {
        return devolucion;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public byte[] getLogo() {
        return logo;
    }

    public FiscalData getFiscalData() {
        return fiscalData;
    }

    public List<LineaTicket> getLineas() {
        return lineas;
    }

    public List<PromocionAplicada> getPromociones() {
        return promociones;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public Map<String, Object> getAtributosAdicionales() {
        return atributosAdicionales;
    }

    public Object getAtributo(String clave) {
        return atributosAdicionales.get(clave);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String documentUid;
        private String codigoTicket;
        private LocalDateTime fechaTicket;
        private LocalDateTime fechaOrigen;
        private String locatorId;
        private String uidInstancia;
        private Pais pais = Pais.ES;
        private boolean devolucion;
        private String numeroPedido;
        private byte[] logo;
        private FiscalData fiscalData;
        private List<LineaTicket> lineas = new ArrayList<>();
        private List<PromocionAplicada> promociones = new ArrayList<>();
        private List<Pago> pagos = new ArrayList<>();
        private Map<String, Object> atributosAdicionales = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder documentUid(String documentUid) {
            this.documentUid = documentUid;
            return this;
        }

        public Builder codigoTicket(String codigoTicket) {
            this.codigoTicket = codigoTicket;
            return this;
        }

        public Builder fechaTicket(LocalDateTime fechaTicket) {
            this.fechaTicket = fechaTicket;
            return this;
        }

        public Builder fechaOrigen(LocalDateTime fechaOrigen) {
            this.fechaOrigen = fechaOrigen;
            return this;
        }

        public Builder locatorId(String locatorId) {
            this.locatorId = locatorId;
            return this;
        }

        public Builder uidInstancia(String uidInstancia) {
            this.uidInstancia = uidInstancia;
            return this;
        }

        public Builder pais(Pais pais) {
            this.pais = pais;
            return this;
        }

        public Builder devolucion(boolean devolucion) {
            this.devolucion = devolucion;
            return this;
        }

        public Builder numeroPedido(String numeroPedido) {
            this.numeroPedido = numeroPedido;
            return this;
        }

        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public Builder fiscalData(FiscalData fiscalData) {
            this.fiscalData = fiscalData;
            return this;
        }

        public Builder lineas(List<LineaTicket> lineas) {
            this.lineas = new ArrayList<>(Objects.requireNonNull(lineas));
            return this;
        }

        public Builder promociones(List<PromocionAplicada> promociones) {
            this.promociones = new ArrayList<>(Objects.requireNonNull(promociones));
            return this;
        }

        public Builder pagos(List<Pago> pagos) {
            this.pagos = new ArrayList<>(Objects.requireNonNull(pagos));
            return this;
        }

        public Builder atributosAdicionales(Map<String, Object> atributosAdicionales) {
            this.atributosAdicionales = new java.util.LinkedHashMap<>(Objects.requireNonNull(atributosAdicionales));
            return this;
        }

        public TicketVentaAbono build() {
            Objects.requireNonNull(documentUid, "documentUid");
            Objects.requireNonNull(codigoTicket, "codigoTicket");
            Objects.requireNonNull(fechaTicket, "fechaTicket");
            Objects.requireNonNull(locatorId, "locatorId");
            Objects.requireNonNull(uidInstancia, "uidInstancia");
            return new TicketVentaAbono(this);
        }
    }

    public enum Pais {
        ES,
        PT,
        CA,
        FR,
        OTHER;

        public static Pais fromCodigo(String codigo) {
            for (Pais value : values()) {
                if (value.name().equalsIgnoreCase(codigo)) {
                    return value;
                }
            }
            return OTHER;
        }
    }

    public static final class FiscalData {
        private final String atcud;
        private final String qrData;

        public FiscalData(String atcud, String qrData) {
            this.atcud = atcud;
            this.qrData = qrData;
        }

        public String getAtcud() {
            return atcud;
        }

        public String getQrData() {
            return qrData;
        }
    }

    public static final class LineaTicket {
        private final String articulo;
        private final String descripcion;
        private final BigDecimal cantidad;
        private final BigDecimal precioUnitario;
        private final BigDecimal descuento;
        private final Map<String, Object> condicionesVenta;

        public LineaTicket(String articulo, String descripcion, BigDecimal cantidad,
                BigDecimal precioUnitario, BigDecimal descuento,
                Map<String, Object> condicionesVenta) {
            this.articulo = articulo;
            this.descripcion = descripcion;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.descuento = descuento;
            this.condicionesVenta = condicionesVenta;
        }

        public String getArticulo() {
            return articulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public BigDecimal getCantidad() {
            return cantidad;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public BigDecimal getDescuento() {
            return descuento;
        }

        public Map<String, Object> getCondicionesVenta() {
            return condicionesVenta;
        }

        public String groupingKey() {
            return articulo + "|" + precioUnitario + "|" + descuento + "|" + condicionesVenta;
        }
    }

    public static final class PromocionAplicada {
        private final String codigo;
        private final String descripcion;
        private final BigDecimal importe;

        public PromocionAplicada(String codigo, String descripcion, BigDecimal importe) {
            this.codigo = codigo;
            this.descripcion = descripcion;
            this.importe = importe;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public BigDecimal getImporte() {
            return importe;
        }
    }

    public static final class Pago {
        private final TipoPago tipo;
        private final String descripcion;
        private final BigDecimal importe;
        private final Map<String, Object> metadatos;

        public Pago(TipoPago tipo, String descripcion, BigDecimal importe, Map<String, Object> metadatos) {
            this.tipo = tipo;
            this.descripcion = descripcion;
            this.importe = importe;
            this.metadatos = metadatos;
        }

        public TipoPago getTipo() {
            return tipo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public BigDecimal getImporte() {
            return importe;
        }

        public Map<String, Object> getMetadatos() {
            return metadatos;
        }
    }

    public enum TipoPago {
        TARJETA,
        EFECTIVO,
        GIFT_CARD,
        OTRO;
    }
}

