package com.comerzzia.pos.persistence.articulos.tarifas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TarifaDetalleExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODTAR = "CODTAR";

    public static final String ORDER_BY_CODTAR_DESC = "CODTAR DESC";

    public static final String ORDER_BY_CODART = "CODART";

    public static final String ORDER_BY_CODART_DESC = "CODART DESC";

    public static final String ORDER_BY_DESGLOSE1 = "DESGLOSE1";

    public static final String ORDER_BY_DESGLOSE1_DESC = "DESGLOSE1 DESC";

    public static final String ORDER_BY_DESGLOSE2 = "DESGLOSE2";

    public static final String ORDER_BY_DESGLOSE2_DESC = "DESGLOSE2 DESC";

    public static final String ORDER_BY_FECHA_INICIO = "FECHA_INICIO";

    public static final String ORDER_BY_FECHA_INICIO_DESC = "FECHA_INICIO DESC";

    public static final String ORDER_BY_PRECIO_COSTO = "PRECIO_COSTO";

    public static final String ORDER_BY_PRECIO_COSTO_DESC = "PRECIO_COSTO DESC";

    public static final String ORDER_BY_FACTOR_MARCAJE = "FACTOR_MARCAJE";

    public static final String ORDER_BY_FACTOR_MARCAJE_DESC = "FACTOR_MARCAJE DESC";

    public static final String ORDER_BY_PRECIO_VENTA = "PRECIO_VENTA";

    public static final String ORDER_BY_PRECIO_VENTA_DESC = "PRECIO_VENTA DESC";

    public static final String ORDER_BY_PRECIO_TOTAL = "PRECIO_TOTAL";

    public static final String ORDER_BY_PRECIO_TOTAL_DESC = "PRECIO_TOTAL DESC";

    public static final String ORDER_BY_VERSION = "VERSION";

    public static final String ORDER_BY_VERSION_DESC = "VERSION DESC";

    public static final String ORDER_BY_PRECIO_VENTA_REF = "PRECIO_VENTA_REF";

    public static final String ORDER_BY_PRECIO_VENTA_REF_DESC = "PRECIO_VENTA_REF DESC";

    public static final String ORDER_BY_PRECIO_VENTA_REF_TOTAL = "PRECIO_VENTA_REF_TOTAL";

    public static final String ORDER_BY_PRECIO_VENTA_REF_TOTAL_DESC = "PRECIO_VENTA_REF_TOTAL DESC";

    public static final String ORDER_BY_BORRADO = "BORRADO";

    public static final String ORDER_BY_BORRADO_DESC = "BORRADO DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TarifaDetalleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition != null) {
                criteria.add(new Criterion(condition));
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value != null) {
                criteria.add(new Criterion(condition, value));
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 != null && value2 != null) {
                criteria.add(new Criterion(condition, value1, value2));
            }
        }

        public Criteria andUidActividadIsNull() {
            addCriterion("UID_ACTIVIDAD is null");
            return (Criteria) this;
        }

        public Criteria andUidActividadIsNotNull() {
            addCriterion("UID_ACTIVIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andUidActividadEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD =", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <>", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThan(String value) {
            addCriterion("UID_ACTIVIDAD >", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD >=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThan(String value) {
            addCriterion("UID_ACTIVIDAD <", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLike(String value) {
            addCriterion("UID_ACTIVIDAD like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotLike(String value) {
            addCriterion("UID_ACTIVIDAD not like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD not in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD not between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodTarifaIsNull() {
            addCriterion("CODTAR is null");
            return (Criteria) this;
        }

        public Criteria andCodTarifaIsNotNull() {
            addCriterion("CODTAR is not null");
            return (Criteria) this;
        }

        public Criteria andCodTarifaEqualTo(String value) {
            addCriterion("CODTAR =", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaNotEqualTo(String value) {
            addCriterion("CODTAR <>", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaGreaterThan(String value) {
            addCriterion("CODTAR >", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaGreaterThanOrEqualTo(String value) {
            addCriterion("CODTAR >=", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaLessThan(String value) {
            addCriterion("CODTAR <", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaLessThanOrEqualTo(String value) {
            addCriterion("CODTAR <=", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaLike(String value) {
            addCriterion("CODTAR like", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaNotLike(String value) {
            addCriterion("CODTAR not like", value, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaIn(List<String> values) {
            addCriterion("CODTAR in", values, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaNotIn(List<String> values) {
            addCriterion("CODTAR not in", values, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaBetween(String value1, String value2) {
            addCriterion("CODTAR between", value1, value2, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaNotBetween(String value1, String value2) {
            addCriterion("CODTAR not between", value1, value2, "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodArticuloIsNull() {
            addCriterion("CODART is null");
            return (Criteria) this;
        }

        public Criteria andCodArticuloIsNotNull() {
            addCriterion("CODART is not null");
            return (Criteria) this;
        }

        public Criteria andCodArticuloEqualTo(String value) {
            addCriterion("CODART =", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloNotEqualTo(String value) {
            addCriterion("CODART <>", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloGreaterThan(String value) {
            addCriterion("CODART >", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloGreaterThanOrEqualTo(String value) {
            addCriterion("CODART >=", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloLessThan(String value) {
            addCriterion("CODART <", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloLessThanOrEqualTo(String value) {
            addCriterion("CODART <=", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloLike(String value) {
            addCriterion("CODART like", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloNotLike(String value) {
            addCriterion("CODART not like", value, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloIn(List<String> values) {
            addCriterion("CODART in", values, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloNotIn(List<String> values) {
            addCriterion("CODART not in", values, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloBetween(String value1, String value2) {
            addCriterion("CODART between", value1, value2, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andCodArticuloNotBetween(String value1, String value2) {
            addCriterion("CODART not between", value1, value2, "codArticulo");
            return (Criteria) this;
        }

        public Criteria andDesglose1IsNull() {
            addCriterion("DESGLOSE1 is null");
            return (Criteria) this;
        }

        public Criteria andDesglose1IsNotNull() {
            addCriterion("DESGLOSE1 is not null");
            return (Criteria) this;
        }

        public Criteria andDesglose1EqualTo(String value) {
            addCriterion("DESGLOSE1 =", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1NotEqualTo(String value) {
            addCriterion("DESGLOSE1 <>", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1GreaterThan(String value) {
            addCriterion("DESGLOSE1 >", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1GreaterThanOrEqualTo(String value) {
            addCriterion("DESGLOSE1 >=", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1LessThan(String value) {
            addCriterion("DESGLOSE1 <", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1LessThanOrEqualTo(String value) {
            addCriterion("DESGLOSE1 <=", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1Like(String value) {
            addCriterion("DESGLOSE1 like", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1NotLike(String value) {
            addCriterion("DESGLOSE1 not like", value, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1In(List<String> values) {
            addCriterion("DESGLOSE1 in", values, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1NotIn(List<String> values) {
            addCriterion("DESGLOSE1 not in", values, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1Between(String value1, String value2) {
            addCriterion("DESGLOSE1 between", value1, value2, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose1NotBetween(String value1, String value2) {
            addCriterion("DESGLOSE1 not between", value1, value2, "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose2IsNull() {
            addCriterion("DESGLOSE2 is null");
            return (Criteria) this;
        }

        public Criteria andDesglose2IsNotNull() {
            addCriterion("DESGLOSE2 is not null");
            return (Criteria) this;
        }

        public Criteria andDesglose2EqualTo(String value) {
            addCriterion("DESGLOSE2 =", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2NotEqualTo(String value) {
            addCriterion("DESGLOSE2 <>", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2GreaterThan(String value) {
            addCriterion("DESGLOSE2 >", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2GreaterThanOrEqualTo(String value) {
            addCriterion("DESGLOSE2 >=", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2LessThan(String value) {
            addCriterion("DESGLOSE2 <", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2LessThanOrEqualTo(String value) {
            addCriterion("DESGLOSE2 <=", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2Like(String value) {
            addCriterion("DESGLOSE2 like", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2NotLike(String value) {
            addCriterion("DESGLOSE2 not like", value, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2In(List<String> values) {
            addCriterion("DESGLOSE2 in", values, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2NotIn(List<String> values) {
            addCriterion("DESGLOSE2 not in", values, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2Between(String value1, String value2) {
            addCriterion("DESGLOSE2 between", value1, value2, "desglose2");
            return (Criteria) this;
        }

        public Criteria andDesglose2NotBetween(String value1, String value2) {
            addCriterion("DESGLOSE2 not between", value1, value2, "desglose2");
            return (Criteria) this;
        }

        public Criteria andFechaInicioIsNull() {
            addCriterion("FECHA_INICIO is null");
            return (Criteria) this;
        }

        public Criteria andFechaInicioIsNotNull() {
            addCriterion("FECHA_INICIO is not null");
            return (Criteria) this;
        }

        public Criteria andFechaInicioEqualTo(Date value) {
            addCriterion("FECHA_INICIO =", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioNotEqualTo(Date value) {
            addCriterion("FECHA_INICIO <>", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioGreaterThan(Date value) {
            addCriterion("FECHA_INICIO >", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_INICIO >=", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioLessThan(Date value) {
            addCriterion("FECHA_INICIO <", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_INICIO <=", value, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioIn(List<Date> values) {
            addCriterion("FECHA_INICIO in", values, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioNotIn(List<Date> values) {
            addCriterion("FECHA_INICIO not in", values, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioBetween(Date value1, Date value2) {
            addCriterion("FECHA_INICIO between", value1, value2, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andFechaInicioNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_INICIO not between", value1, value2, "fechaInicio");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoIsNull() {
            addCriterion("PRECIO_COSTO is null");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoIsNotNull() {
            addCriterion("PRECIO_COSTO is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoEqualTo(BigDecimal value) {
            addCriterion("PRECIO_COSTO =", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoNotEqualTo(BigDecimal value) {
            addCriterion("PRECIO_COSTO <>", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoGreaterThan(BigDecimal value) {
            addCriterion("PRECIO_COSTO >", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_COSTO >=", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoLessThan(BigDecimal value) {
            addCriterion("PRECIO_COSTO <", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_COSTO <=", value, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoIn(List<BigDecimal> values) {
            addCriterion("PRECIO_COSTO in", values, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoNotIn(List<BigDecimal> values) {
            addCriterion("PRECIO_COSTO not in", values, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_COSTO between", value1, value2, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andPrecioCostoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_COSTO not between", value1, value2, "precioCosto");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeIsNull() {
            addCriterion("FACTOR_MARCAJE is null");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeIsNotNull() {
            addCriterion("FACTOR_MARCAJE is not null");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeEqualTo(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE =", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeNotEqualTo(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE <>", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeGreaterThan(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE >", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE >=", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeLessThan(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE <", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FACTOR_MARCAJE <=", value, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeIn(List<BigDecimal> values) {
            addCriterion("FACTOR_MARCAJE in", values, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeNotIn(List<BigDecimal> values) {
            addCriterion("FACTOR_MARCAJE not in", values, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FACTOR_MARCAJE between", value1, value2, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andFactorMarcajeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FACTOR_MARCAJE not between", value1, value2, "factorMarcaje");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaIsNull() {
            addCriterion("PRECIO_VENTA is null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaIsNotNull() {
            addCriterion("PRECIO_VENTA is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA =", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaNotEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA <>", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaGreaterThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA >", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA >=", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaLessThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA <", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA <=", value, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA in", values, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaNotIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA not in", values, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA between", value1, value2, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA not between", value1, value2, "precioVenta");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalIsNull() {
            addCriterion("PRECIO_TOTAL is null");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalIsNotNull() {
            addCriterion("PRECIO_TOTAL is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalEqualTo(BigDecimal value) {
            addCriterion("PRECIO_TOTAL =", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalNotEqualTo(BigDecimal value) {
            addCriterion("PRECIO_TOTAL <>", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalGreaterThan(BigDecimal value) {
            addCriterion("PRECIO_TOTAL >", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_TOTAL >=", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalLessThan(BigDecimal value) {
            addCriterion("PRECIO_TOTAL <", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_TOTAL <=", value, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalIn(List<BigDecimal> values) {
            addCriterion("PRECIO_TOTAL in", values, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalNotIn(List<BigDecimal> values) {
            addCriterion("PRECIO_TOTAL not in", values, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_TOTAL between", value1, value2, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_TOTAL not between", value1, value2, "precioTotal");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefIsNull() {
            addCriterion("PRECIO_VENTA_REF is null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefIsNotNull() {
            addCriterion("PRECIO_VENTA_REF is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF =", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefNotEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF <>", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefGreaterThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF >", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF >=", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefLessThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF <", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF <=", value, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA_REF in", values, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefNotIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA_REF not in", values, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA_REF between", value1, value2, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA_REF not between", value1, value2, "precioVentaRef");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalIsNull() {
            addCriterion("PRECIO_VENTA_REF_TOTAL is null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalIsNotNull() {
            addCriterion("PRECIO_VENTA_REF_TOTAL is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL =", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalNotEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL <>", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalGreaterThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL >", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL >=", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalLessThan(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL <", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRECIO_VENTA_REF_TOTAL <=", value, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA_REF_TOTAL in", values, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalNotIn(List<BigDecimal> values) {
            addCriterion("PRECIO_VENTA_REF_TOTAL not in", values, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA_REF_TOTAL between", value1, value2, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andPrecioVentaRefTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRECIO_VENTA_REF_TOTAL not between", value1, value2, "precioVentaRefTotal");
            return (Criteria) this;
        }

        public Criteria andBorradoIsNull() {
            addCriterion("BORRADO is null");
            return (Criteria) this;
        }

        public Criteria andBorradoIsNotNull() {
            addCriterion("BORRADO is not null");
            return (Criteria) this;
        }

        public Criteria andBorradoEqualTo(String value) {
            addCriterion("BORRADO =", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotEqualTo(String value) {
            addCriterion("BORRADO <>", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThan(String value) {
            addCriterion("BORRADO >", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThanOrEqualTo(String value) {
            addCriterion("BORRADO >=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThan(String value) {
            addCriterion("BORRADO <", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThanOrEqualTo(String value) {
            addCriterion("BORRADO <=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLike(String value) {
            addCriterion("BORRADO like", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotLike(String value) {
            addCriterion("BORRADO not like", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoIn(List<String> values) {
            addCriterion("BORRADO in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotIn(List<String> values) {
            addCriterion("BORRADO not in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoBetween(String value1, String value2) {
            addCriterion("BORRADO between", value1, value2, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotBetween(String value1, String value2) {
            addCriterion("BORRADO not between", value1, value2, "borrado");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodTarifaLikeInsensitive(String value) {
            addCriterion("upper(CODTAR) like", value.toUpperCase(), "codTarifa");
            return (Criteria) this;
        }

        public Criteria andCodArticuloLikeInsensitive(String value) {
            addCriterion("upper(CODART) like", value.toUpperCase(), "codArticulo");
            return (Criteria) this;
        }

        public Criteria andDesglose1LikeInsensitive(String value) {
            addCriterion("upper(DESGLOSE1) like", value.toUpperCase(), "desglose1");
            return (Criteria) this;
        }

        public Criteria andDesglose2LikeInsensitive(String value) {
            addCriterion("upper(DESGLOSE2) like", value.toUpperCase(), "desglose2");
            return (Criteria) this;
        }

        public Criteria andBorradoLikeInsensitive(String value) {
            addCriterion("upper(BORRADO) like", value.toUpperCase(), "borrado");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}