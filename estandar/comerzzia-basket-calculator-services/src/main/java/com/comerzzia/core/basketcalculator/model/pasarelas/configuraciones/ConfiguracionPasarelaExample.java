package com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionPasarelaExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_CONF_PASARELA = "ID_CONF_PASARELA";

    public static final String ORDER_BY_ID_CONF_PASARELA_DESC = "ID_CONF_PASARELA DESC";

    public static final String ORDER_BY_ID_TIPO_PASARELA = "ID_TIPO_PASARELA";

    public static final String ORDER_BY_ID_TIPO_PASARELA_DESC = "ID_TIPO_PASARELA DESC";

    public static final String ORDER_BY_DESCONFPASARELA = "DESCONFPASARELA";

    public static final String ORDER_BY_DESCONFPASARELA_DESC = "DESCONFPASARELA DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_CONFIGURACION = "CONFIGURACION";

    public static final String ORDER_BY_CONFIGURACION_DESC = "CONFIGURACION DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConfiguracionPasarelaExample() {
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
        protected List<Criterion> activoCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getActivoCriteria() {
            return activoCriteria;
        }

        protected void addActivoCriterion(String condition, Object value, String property) {
            if (value != null) {
                activoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addActivoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                activoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || activoCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(activoCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition != null) {
                criteria.add(new Criterion(condition));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value != null) {
                criteria.add(new Criterion(condition, value));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 != null && value2 != null) {
                criteria.add(new Criterion(condition, value1, value2));
                allCriteria = null;
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

        public Criteria andDesconfpasarelaIsNull() {
            addCriterion("DESCONFPASARELA is null");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaIsNotNull() {
            addCriterion("DESCONFPASARELA is not null");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaEqualTo(String value) {
            addCriterion("DESCONFPASARELA =", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaNotEqualTo(String value) {
            addCriterion("DESCONFPASARELA <>", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaGreaterThan(String value) {
            addCriterion("DESCONFPASARELA >", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaGreaterThanOrEqualTo(String value) {
            addCriterion("DESCONFPASARELA >=", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaLessThan(String value) {
            addCriterion("DESCONFPASARELA <", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaLessThanOrEqualTo(String value) {
            addCriterion("DESCONFPASARELA <=", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaLike(String value) {
            addCriterion("DESCONFPASARELA like", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaNotLike(String value) {
            addCriterion("DESCONFPASARELA not like", value, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaIn(List<String> values) {
            addCriterion("DESCONFPASARELA in", values, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaNotIn(List<String> values) {
            addCriterion("DESCONFPASARELA not in", values, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaBetween(String value1, String value2) {
            addCriterion("DESCONFPASARELA between", value1, value2, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaNotBetween(String value1, String value2) {
            addCriterion("DESCONFPASARELA not between", value1, value2, "desconfpasarela");
            return (Criteria) this;
        }

        public Criteria andActivoIsNull() {
            addCriterion("ACTIVO is null");
            return (Criteria) this;
        }

        public Criteria andActivoIsNotNull() {
            addCriterion("ACTIVO is not null");
            return (Criteria) this;
        }

        public Criteria andActivoEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO =", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <>", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThan(Boolean value) {
            addActivoCriterion("ACTIVO >", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO >=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThan(Boolean value) {
            addActivoCriterion("ACTIVO <", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLike(Boolean value) {
            addActivoCriterion("ACTIVO like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotLike(Boolean value) {
            addActivoCriterion("ACTIVO not like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO not in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO not between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andIdConfPasarelaLikeInsensitive(String value) {
            addCriterion("upper(ID_CONF_PASARELA) like", value.toUpperCase(), "idConfPasarela");
            return (Criteria) this;
        }

        public Criteria andIdTipoPasarelaLikeInsensitive(String value) {
            addCriterion("upper(ID_TIPO_PASARELA) like", value.toUpperCase(), "idTipoPasarela");
            return (Criteria) this;
        }

        public Criteria andDesconfpasarelaLikeInsensitive(String value) {
            addCriterion("upper(DESCONFPASARELA) like", value.toUpperCase(), "desconfpasarela");
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