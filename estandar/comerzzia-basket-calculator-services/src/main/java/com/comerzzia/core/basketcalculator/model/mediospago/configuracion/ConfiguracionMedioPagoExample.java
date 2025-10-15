package com.comerzzia.core.basketcalculator.model.mediospago.configuracion;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionMedioPagoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_CLASE = "ID_CLASE";

    public static final String ORDER_BY_ID_CLASE_DESC = "ID_CLASE DESC";

    public static final String ORDER_BY_ID_OBJETO = "ID_OBJETO";

    public static final String ORDER_BY_ID_OBJETO_DESC = "ID_OBJETO DESC";

    public static final String ORDER_BY_CODMEDPAG = "CODMEDPAG";

    public static final String ORDER_BY_CODMEDPAG_DESC = "CODMEDPAG DESC";

    public static final String ORDER_BY_ID_CONF_PASARELA = "ID_CONF_PASARELA";

    public static final String ORDER_BY_ID_CONF_PASARELA_DESC = "ID_CONF_PASARELA DESC";

    public static final String ORDER_BY_DESMEDPAG = "DESMEDPAG";

    public static final String ORDER_BY_DESMEDPAG_DESC = "DESMEDPAG DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_DESCLASE = "DESCLASE";

    public static final String ORDER_BY_DESCLASE_DESC = "DESCLASE DESC";

    public static final String ORDER_BY_ID_TIPO_PASARELA = "ID_TIPO_PASARELA";

    public static final String ORDER_BY_ID_TIPO_PASARELA_DESC = "ID_TIPO_PASARELA DESC";

    public static final String ORDER_BY_DESTIPOPASARELA = "DESTIPOPASARELA";

    public static final String ORDER_BY_DESTIPOPASARELA_DESC = "DESTIPOPASARELA DESC";

    public static final String ORDER_BY_DESCONFPASARELA = "DESCONFPASARELA";

    public static final String ORDER_BY_DESCONFPASARELA_DESC = "DESCONFPASARELA DESC";

    public static final String ORDER_BY_CONF_ACTIVO = "CONF_ACTIVO";

    public static final String ORDER_BY_CONF_ACTIVO_DESC = "CONF_ACTIVO DESC";
    
    public static final String ORDER_BY_CLASE_CONTROL = "CLASE_CONTROL";

    public static final String ORDER_BY_CLASE_CONTROL_DESC = "CLASE_CONTROL DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConfiguracionMedioPagoExample() {
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

        public Criteria andIdClaseIsNull() {
            addCriterion("ID_CLASE is null");
            return (Criteria) this;
        }

        public Criteria andIdClaseIsNotNull() {
            addCriterion("ID_CLASE is not null");
            return (Criteria) this;
        }

        public Criteria andIdClaseEqualTo(String value) {
            addCriterion("ID_CLASE =", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseNotEqualTo(String value) {
            addCriterion("ID_CLASE <>", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseGreaterThan(String value) {
            addCriterion("ID_CLASE >", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseGreaterThanOrEqualTo(String value) {
            addCriterion("ID_CLASE >=", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseLessThan(String value) {
            addCriterion("ID_CLASE <", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseLessThanOrEqualTo(String value) {
            addCriterion("ID_CLASE <=", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseLike(String value) {
            addCriterion("ID_CLASE like", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseNotLike(String value) {
            addCriterion("ID_CLASE not like", value, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseIn(List<String> values) {
            addCriterion("ID_CLASE in", values, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseNotIn(List<String> values) {
            addCriterion("ID_CLASE not in", values, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseBetween(String value1, String value2) {
            addCriterion("ID_CLASE between", value1, value2, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdClaseNotBetween(String value1, String value2) {
            addCriterion("ID_CLASE not between", value1, value2, "idClase");
            return (Criteria) this;
        }

        public Criteria andIdObjetoIsNull() {
            addCriterion("ID_OBJETO is null");
            return (Criteria) this;
        }

        public Criteria andIdObjetoIsNotNull() {
            addCriterion("ID_OBJETO is not null");
            return (Criteria) this;
        }

        public Criteria andIdObjetoEqualTo(String value) {
            addCriterion("ID_OBJETO =", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoNotEqualTo(String value) {
            addCriterion("ID_OBJETO <>", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoGreaterThan(String value) {
            addCriterion("ID_OBJETO >", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoGreaterThanOrEqualTo(String value) {
            addCriterion("ID_OBJETO >=", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoLessThan(String value) {
            addCriterion("ID_OBJETO <", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoLessThanOrEqualTo(String value) {
            addCriterion("ID_OBJETO <=", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoLike(String value) {
            addCriterion("ID_OBJETO like", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoNotLike(String value) {
            addCriterion("ID_OBJETO not like", value, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoIn(List<String> values) {
            addCriterion("ID_OBJETO in", values, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoNotIn(List<String> values) {
            addCriterion("ID_OBJETO not in", values, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoBetween(String value1, String value2) {
            addCriterion("ID_OBJETO between", value1, value2, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andIdObjetoNotBetween(String value1, String value2) {
            addCriterion("ID_OBJETO not between", value1, value2, "idObjeto");
            return (Criteria) this;
        }

        public Criteria andCodmedpagIsNull() {
            addCriterion("CODMEDPAG is null");
            return (Criteria) this;
        }

        public Criteria andCodmedpagIsNotNull() {
            addCriterion("CODMEDPAG is not null");
            return (Criteria) this;
        }

        public Criteria andCodmedpagEqualTo(String value) {
            addCriterion("CODMEDPAG =", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagNotEqualTo(String value) {
            addCriterion("CODMEDPAG <>", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagGreaterThan(String value) {
            addCriterion("CODMEDPAG >", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG >=", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagLessThan(String value) {
            addCriterion("CODMEDPAG <", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG <=", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagLike(String value) {
            addCriterion("CODMEDPAG like", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagNotLike(String value) {
            addCriterion("CODMEDPAG not like", value, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagIn(List<String> values) {
            addCriterion("CODMEDPAG in", values, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagNotIn(List<String> values) {
            addCriterion("CODMEDPAG not in", values, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagBetween(String value1, String value2) {
            addCriterion("CODMEDPAG between", value1, value2, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andCodmedpagNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG not between", value1, value2, "codmedpag");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaIsNull() {
            addCriterion("ID_CONF_PASARELA is null");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaIsNotNull() {
            addCriterion("ID_CONF_PASARELA is not null");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaEqualTo(String value) {
            addCriterion("ID_CONF_PASARELA =", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaNotEqualTo(String value) {
            addCriterion("ID_CONF_PASARELA <>", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaGreaterThan(String value) {
            addCriterion("ID_CONF_PASARELA >", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaGreaterThanOrEqualTo(String value) {
            addCriterion("ID_CONF_PASARELA >=", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaLessThan(String value) {
            addCriterion("ID_CONF_PASARELA <", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaLessThanOrEqualTo(String value) {
            addCriterion("ID_CONF_PASARELA <=", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaLike(String value) {
            addCriterion("ID_CONF_PASARELA like", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaNotLike(String value) {
            addCriterion("ID_CONF_PASARELA not like", value, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaIn(List<String> values) {
            addCriterion("ID_CONF_PASARELA in", values, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaNotIn(List<String> values) {
            addCriterion("ID_CONF_PASARELA not in", values, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaBetween(String value1, String value2) {
            addCriterion("ID_CONF_PASARELA between", value1, value2, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaNotBetween(String value1, String value2) {
            addCriterion("ID_CONF_PASARELA not between", value1, value2, "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andIdClaseLikeInsensitive(String value) {
            addCriterion("upper(ID_CLASE) like", value.toUpperCase(), "idClase");
            return (Criteria) this;
        }

        public Criteria andIdObjetoLikeInsensitive(String value) {
            addCriterion("upper(ID_OBJETO) like", value.toUpperCase(), "idObjeto");
            return (Criteria) this;
        }

        public Criteria andCodmedpagLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG) like", value.toUpperCase(), "codmedpag");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaLikeInsensitive(String value) {
            addCriterion("upper(ID_CONF_PASARELA) like", value.toUpperCase(), "idConfPasarela");
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