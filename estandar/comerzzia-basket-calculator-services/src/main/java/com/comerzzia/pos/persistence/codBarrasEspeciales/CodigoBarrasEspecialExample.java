/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.codBarrasEspeciales;

import java.util.ArrayList;
import java.util.List;

public class CodigoBarrasEspecialExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ORDEN = "ORDEN";

    public static final String ORDER_BY_ORDEN_DESC = "ORDEN DESC";

    public static final String ORDER_BY_DESCRIPCION = "DESCRIPCION";

    public static final String ORDER_BY_DESCRIPCION_DESC = "DESCRIPCION DESC";

    public static final String ORDER_BY_PREFIJO = "PREFIJO";

    public static final String ORDER_BY_PREFIJO_DESC = "PREFIJO DESC";

    public static final String ORDER_BY_CODART = "CODART";

    public static final String ORDER_BY_CODART_DESC = "CODART DESC";

    public static final String ORDER_BY_PRECIO = "PRECIO";

    public static final String ORDER_BY_PRECIO_DESC = "PRECIO DESC";

    public static final String ORDER_BY_CANTIDAD = "CANTIDAD";

    public static final String ORDER_BY_CANTIDAD_DESC = "CANTIDAD DESC";

    public static final String ORDER_BY_FIDELIZACION = "FIDELIZACION";

    public static final String ORDER_BY_FIDELIZACION_DESC = "FIDELIZACION DESC";

    public static final String ORDER_BY_CODTICKET = "CODTICKET";

    public static final String ORDER_BY_CODTICKET_DESC = "CODTICKET DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CodigoBarrasEspecialExample() {
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

        public Criteria andOrdenIsNull() {
            addCriterion("ORDEN is null");
            return (Criteria) this;
        }

        public Criteria andOrdenIsNotNull() {
            addCriterion("ORDEN is not null");
            return (Criteria) this;
        }

        public Criteria andOrdenEqualTo(Integer value) {
            addCriterion("ORDEN =", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenNotEqualTo(Integer value) {
            addCriterion("ORDEN <>", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenGreaterThan(Integer value) {
            addCriterion("ORDEN >", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDEN >=", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenLessThan(Integer value) {
            addCriterion("ORDEN <", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenLessThanOrEqualTo(Integer value) {
            addCriterion("ORDEN <=", value, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenIn(List<Integer> values) {
            addCriterion("ORDEN in", values, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenNotIn(List<Integer> values) {
            addCriterion("ORDEN not in", values, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenBetween(Integer value1, Integer value2) {
            addCriterion("ORDEN between", value1, value2, "orden");
            return (Criteria) this;
        }

        public Criteria andOrdenNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDEN not between", value1, value2, "orden");
            return (Criteria) this;
        }

        public Criteria andDescripcionIsNull() {
            addCriterion("DESCRIPCION is null");
            return (Criteria) this;
        }

        public Criteria andDescripcionIsNotNull() {
            addCriterion("DESCRIPCION is not null");
            return (Criteria) this;
        }

        public Criteria andDescripcionEqualTo(String value) {
            addCriterion("DESCRIPCION =", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotEqualTo(String value) {
            addCriterion("DESCRIPCION <>", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionGreaterThan(String value) {
            addCriterion("DESCRIPCION >", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION >=", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLessThan(String value) {
            addCriterion("DESCRIPCION <", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION <=", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLike(String value) {
            addCriterion("DESCRIPCION like", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotLike(String value) {
            addCriterion("DESCRIPCION not like", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionIn(List<String> values) {
            addCriterion("DESCRIPCION in", values, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotIn(List<String> values) {
            addCriterion("DESCRIPCION not in", values, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionBetween(String value1, String value2) {
            addCriterion("DESCRIPCION between", value1, value2, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPCION not between", value1, value2, "descripcion");
            return (Criteria) this;
        }

        public Criteria andPrefijoIsNull() {
            addCriterion("PREFIJO is null");
            return (Criteria) this;
        }

        public Criteria andPrefijoIsNotNull() {
            addCriterion("PREFIJO is not null");
            return (Criteria) this;
        }

        public Criteria andPrefijoEqualTo(String value) {
            addCriterion("PREFIJO =", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoNotEqualTo(String value) {
            addCriterion("PREFIJO <>", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoGreaterThan(String value) {
            addCriterion("PREFIJO >", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoGreaterThanOrEqualTo(String value) {
            addCriterion("PREFIJO >=", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoLessThan(String value) {
            addCriterion("PREFIJO <", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoLessThanOrEqualTo(String value) {
            addCriterion("PREFIJO <=", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoLike(String value) {
            addCriterion("PREFIJO like", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoNotLike(String value) {
            addCriterion("PREFIJO not like", value, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoIn(List<String> values) {
            addCriterion("PREFIJO in", values, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoNotIn(List<String> values) {
            addCriterion("PREFIJO not in", values, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoBetween(String value1, String value2) {
            addCriterion("PREFIJO between", value1, value2, "prefijo");
            return (Criteria) this;
        }

        public Criteria andPrefijoNotBetween(String value1, String value2) {
            addCriterion("PREFIJO not between", value1, value2, "prefijo");
            return (Criteria) this;
        }

        public Criteria andCodartIsNull() {
            addCriterion("CODART is null");
            return (Criteria) this;
        }

        public Criteria andCodartIsNotNull() {
            addCriterion("CODART is not null");
            return (Criteria) this;
        }

        public Criteria andCodartEqualTo(String value) {
            addCriterion("CODART =", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartNotEqualTo(String value) {
            addCriterion("CODART <>", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartGreaterThan(String value) {
            addCriterion("CODART >", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartGreaterThanOrEqualTo(String value) {
            addCriterion("CODART >=", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartLessThan(String value) {
            addCriterion("CODART <", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartLessThanOrEqualTo(String value) {
            addCriterion("CODART <=", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartLike(String value) {
            addCriterion("CODART like", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartNotLike(String value) {
            addCriterion("CODART not like", value, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartIn(List<String> values) {
            addCriterion("CODART in", values, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartNotIn(List<String> values) {
            addCriterion("CODART not in", values, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartBetween(String value1, String value2) {
            addCriterion("CODART between", value1, value2, "codart");
            return (Criteria) this;
        }

        public Criteria andCodartNotBetween(String value1, String value2) {
            addCriterion("CODART not between", value1, value2, "codart");
            return (Criteria) this;
        }

        public Criteria andPrecioIsNull() {
            addCriterion("PRECIO is null");
            return (Criteria) this;
        }

        public Criteria andPrecioIsNotNull() {
            addCriterion("PRECIO is not null");
            return (Criteria) this;
        }

        public Criteria andPrecioEqualTo(String value) {
            addCriterion("PRECIO =", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioNotEqualTo(String value) {
            addCriterion("PRECIO <>", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioGreaterThan(String value) {
            addCriterion("PRECIO >", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioGreaterThanOrEqualTo(String value) {
            addCriterion("PRECIO >=", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioLessThan(String value) {
            addCriterion("PRECIO <", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioLessThanOrEqualTo(String value) {
            addCriterion("PRECIO <=", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioLike(String value) {
            addCriterion("PRECIO like", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioNotLike(String value) {
            addCriterion("PRECIO not like", value, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioIn(List<String> values) {
            addCriterion("PRECIO in", values, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioNotIn(List<String> values) {
            addCriterion("PRECIO not in", values, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioBetween(String value1, String value2) {
            addCriterion("PRECIO between", value1, value2, "precio");
            return (Criteria) this;
        }

        public Criteria andPrecioNotBetween(String value1, String value2) {
            addCriterion("PRECIO not between", value1, value2, "precio");
            return (Criteria) this;
        }

        public Criteria andCantidadIsNull() {
            addCriterion("CANTIDAD is null");
            return (Criteria) this;
        }

        public Criteria andCantidadIsNotNull() {
            addCriterion("CANTIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andCantidadEqualTo(String value) {
            addCriterion("CANTIDAD =", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadNotEqualTo(String value) {
            addCriterion("CANTIDAD <>", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadGreaterThan(String value) {
            addCriterion("CANTIDAD >", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadGreaterThanOrEqualTo(String value) {
            addCriterion("CANTIDAD >=", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadLessThan(String value) {
            addCriterion("CANTIDAD <", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadLessThanOrEqualTo(String value) {
            addCriterion("CANTIDAD <=", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadLike(String value) {
            addCriterion("CANTIDAD like", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadNotLike(String value) {
            addCriterion("CANTIDAD not like", value, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadIn(List<String> values) {
            addCriterion("CANTIDAD in", values, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadNotIn(List<String> values) {
            addCriterion("CANTIDAD not in", values, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadBetween(String value1, String value2) {
            addCriterion("CANTIDAD between", value1, value2, "cantidad");
            return (Criteria) this;
        }

        public Criteria andCantidadNotBetween(String value1, String value2) {
            addCriterion("CANTIDAD not between", value1, value2, "cantidad");
            return (Criteria) this;
        }

        public Criteria andFidelizacionIsNull() {
            addCriterion("FIDELIZACION is null");
            return (Criteria) this;
        }

        public Criteria andFidelizacionIsNotNull() {
            addCriterion("FIDELIZACION is not null");
            return (Criteria) this;
        }

        public Criteria andFidelizacionEqualTo(String value) {
            addCriterion("FIDELIZACION =", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionNotEqualTo(String value) {
            addCriterion("FIDELIZACION <>", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionGreaterThan(String value) {
            addCriterion("FIDELIZACION >", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionGreaterThanOrEqualTo(String value) {
            addCriterion("FIDELIZACION >=", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionLessThan(String value) {
            addCriterion("FIDELIZACION <", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionLessThanOrEqualTo(String value) {
            addCriterion("FIDELIZACION <=", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionLike(String value) {
            addCriterion("FIDELIZACION like", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionNotLike(String value) {
            addCriterion("FIDELIZACION not like", value, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionIn(List<String> values) {
            addCriterion("FIDELIZACION in", values, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionNotIn(List<String> values) {
            addCriterion("FIDELIZACION not in", values, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionBetween(String value1, String value2) {
            addCriterion("FIDELIZACION between", value1, value2, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andFidelizacionNotBetween(String value1, String value2) {
            addCriterion("FIDELIZACION not between", value1, value2, "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andCodticketIsNull() {
            addCriterion("CODTICKET is null");
            return (Criteria) this;
        }

        public Criteria andCodticketIsNotNull() {
            addCriterion("CODTICKET is not null");
            return (Criteria) this;
        }

        public Criteria andCodticketEqualTo(String value) {
            addCriterion("CODTICKET =", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketNotEqualTo(String value) {
            addCriterion("CODTICKET <>", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketGreaterThan(String value) {
            addCriterion("CODTICKET >", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketGreaterThanOrEqualTo(String value) {
            addCriterion("CODTICKET >=", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketLessThan(String value) {
            addCriterion("CODTICKET <", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketLessThanOrEqualTo(String value) {
            addCriterion("CODTICKET <=", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketLike(String value) {
            addCriterion("CODTICKET like", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketNotLike(String value) {
            addCriterion("CODTICKET not like", value, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketIn(List<String> values) {
            addCriterion("CODTICKET in", values, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketNotIn(List<String> values) {
            addCriterion("CODTICKET not in", values, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketBetween(String value1, String value2) {
            addCriterion("CODTICKET between", value1, value2, "codticket");
            return (Criteria) this;
        }

        public Criteria andCodticketNotBetween(String value1, String value2) {
            addCriterion("CODTICKET not between", value1, value2, "codticket");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andDescripcionLikeInsensitive(String value) {
            addCriterion("upper(DESCRIPCION) like", value.toUpperCase(), "descripcion");
            return (Criteria) this;
        }

        public Criteria andPrefijoLikeInsensitive(String value) {
            addCriterion("upper(PREFIJO) like", value.toUpperCase(), "prefijo");
            return (Criteria) this;
        }

        public Criteria andCodartLikeInsensitive(String value) {
            addCriterion("upper(CODART) like", value.toUpperCase(), "codart");
            return (Criteria) this;
        }

        public Criteria andPrecioLikeInsensitive(String value) {
            addCriterion("upper(PRECIO) like", value.toUpperCase(), "precio");
            return (Criteria) this;
        }

        public Criteria andCantidadLikeInsensitive(String value) {
            addCriterion("upper(CANTIDAD) like", value.toUpperCase(), "cantidad");
            return (Criteria) this;
        }

        public Criteria andFidelizacionLikeInsensitive(String value) {
            addCriterion("upper(FIDELIZACION) like", value.toUpperCase(), "fidelizacion");
            return (Criteria) this;
        }

        public Criteria andCodticketLikeInsensitive(String value) {
            addCriterion("upper(CODTICKET) like", value.toUpperCase(), "codticket");
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