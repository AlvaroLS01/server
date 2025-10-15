package com.comerzzia.core.basketcalculator.model.pasarelas.tipos;

import java.util.ArrayList;
import java.util.List;

public class TipoPasarelaExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_TIPO_PASARELA = "ID_TIPO_PASARELA";

    public static final String ORDER_BY_ID_TIPO_PASARELA_DESC = "ID_TIPO_PASARELA DESC";

    public static final String ORDER_BY_DESTIPOPASARELA = "DESTIPOPASARELA";

    public static final String ORDER_BY_DESTIPOPASARELA_DESC = "DESTIPOPASARELA DESC";

    public static final String ORDER_BY_CLASE_CONTROL = "CLASE_CONTROL";

    public static final String ORDER_BY_CLASE_CONTROL_DESC = "CLASE_CONTROL DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TipoPasarelaExample() {
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

        public Criteria andIdTipoPasarelaIsNull() {
            addCriterion("ID_TIPO_PASARELA is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaIsNotNull() {
            addCriterion("ID_TIPO_PASARELA is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaEqualTo(String value) {
            addCriterion("ID_TIPO_PASARELA =", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaNotEqualTo(String value) {
            addCriterion("ID_TIPO_PASARELA <>", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaGreaterThan(String value) {
            addCriterion("ID_TIPO_PASARELA >", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaGreaterThanOrEqualTo(String value) {
            addCriterion("ID_TIPO_PASARELA >=", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaLessThan(String value) {
            addCriterion("ID_TIPO_PASARELA <", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaLessThanOrEqualTo(String value) {
            addCriterion("ID_TIPO_PASARELA <=", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaLike(String value) {
            addCriterion("ID_TIPO_PASARELA like", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaNotLike(String value) {
            addCriterion("ID_TIPO_PASARELA not like", value, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaIn(List<String> values) {
            addCriterion("ID_TIPO_PASARELA in", values, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaNotIn(List<String> values) {
            addCriterion("ID_TIPO_PASARELA not in", values, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaBetween(String value1, String value2) {
            addCriterion("ID_TIPO_PASARELA between", value1, value2, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaNotBetween(String value1, String value2) {
            addCriterion("ID_TIPO_PASARELA not between", value1, value2, "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaIsNull() {
            addCriterion("DESTIPOPASARELA is null");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaIsNotNull() {
            addCriterion("DESTIPOPASARELA is not null");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaEqualTo(String value) {
            addCriterion("DESTIPOPASARELA =", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaNotEqualTo(String value) {
            addCriterion("DESTIPOPASARELA <>", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaGreaterThan(String value) {
            addCriterion("DESTIPOPASARELA >", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaGreaterThanOrEqualTo(String value) {
            addCriterion("DESTIPOPASARELA >=", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaLessThan(String value) {
            addCriterion("DESTIPOPASARELA <", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaLessThanOrEqualTo(String value) {
            addCriterion("DESTIPOPASARELA <=", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaLike(String value) {
            addCriterion("DESTIPOPASARELA like", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaNotLike(String value) {
            addCriterion("DESTIPOPASARELA not like", value, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaIn(List<String> values) {
            addCriterion("DESTIPOPASARELA in", values, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaNotIn(List<String> values) {
            addCriterion("DESTIPOPASARELA not in", values, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaBetween(String value1, String value2) {
            addCriterion("DESTIPOPASARELA between", value1, value2, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaNotBetween(String value1, String value2) {
            addCriterion("DESTIPOPASARELA not between", value1, value2, "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andClaseControlIsNull() {
            addCriterion("CLASE_CONTROL is null");
            return (Criteria) this;
        }

        public Criteria andClaseControlIsNotNull() {
            addCriterion("CLASE_CONTROL is not null");
            return (Criteria) this;
        }

        public Criteria andClaseControlEqualTo(String value) {
            addCriterion("CLASE_CONTROL =", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlNotEqualTo(String value) {
            addCriterion("CLASE_CONTROL <>", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlGreaterThan(String value) {
            addCriterion("CLASE_CONTROL >", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlGreaterThanOrEqualTo(String value) {
            addCriterion("CLASE_CONTROL >=", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlLessThan(String value) {
            addCriterion("CLASE_CONTROL <", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlLessThanOrEqualTo(String value) {
            addCriterion("CLASE_CONTROL <=", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlLike(String value) {
            addCriterion("CLASE_CONTROL like", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlNotLike(String value) {
            addCriterion("CLASE_CONTROL not like", value, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlIn(List<String> values) {
            addCriterion("CLASE_CONTROL in", values, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlNotIn(List<String> values) {
            addCriterion("CLASE_CONTROL not in", values, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlBetween(String value1, String value2) {
            addCriterion("CLASE_CONTROL between", value1, value2, "claseControl");
            return (Criteria) this;
        }

        public Criteria andClaseControlNotBetween(String value1, String value2) {
            addCriterion("CLASE_CONTROL not between", value1, value2, "claseControl");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaLikeInsensitive(String value) {
            addCriterion("upper(ID_TIPO_PASARELA) like", value.toUpperCase(), "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andDestipopasarelaLikeInsensitive(String value) {
            addCriterion("upper(DESTIPOPASARELA) like", value.toUpperCase(), "destipopasarela");
            return (Criteria) this;
        }

        public Criteria andClaseControlLikeInsensitive(String value) {
            addCriterion("upper(CLASE_CONTROL) like", value.toUpperCase(), "claseControl");
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