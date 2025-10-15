package com.comerzzia.pos.persistence.core.usuarios.almacenes;

import java.util.ArrayList;
import java.util.List;

public class AlmacenUsuarioExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODALM = "CODALM";

    public static final String ORDER_BY_CODALM_DESC = "CODALM DESC";

    public static final String ORDER_BY_UID_INSTANCIA = "UID_INSTANCIA";

    public static final String ORDER_BY_UID_INSTANCIA_DESC = "UID_INSTANCIA DESC";

    public static final String ORDER_BY_ID_USUARIO = "ID_USUARIO";

    public static final String ORDER_BY_ID_USUARIO_DESC = "ID_USUARIO DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AlmacenUsuarioExample() {
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

        public Criteria andCodalmIsNull() {
            addCriterion("CODALM is null");
            return (Criteria) this;
        }

        public Criteria andCodalmIsNotNull() {
            addCriterion("CODALM is not null");
            return (Criteria) this;
        }

        public Criteria andCodalmEqualTo(String value) {
            addCriterion("CODALM =", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmNotEqualTo(String value) {
            addCriterion("CODALM <>", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmGreaterThan(String value) {
            addCriterion("CODALM >", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM >=", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmLessThan(String value) {
            addCriterion("CODALM <", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmLessThanOrEqualTo(String value) {
            addCriterion("CODALM <=", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmLike(String value) {
            addCriterion("CODALM like", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmNotLike(String value) {
            addCriterion("CODALM not like", value, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmIn(List<String> values) {
            addCriterion("CODALM in", values, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmNotIn(List<String> values) {
            addCriterion("CODALM not in", values, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmBetween(String value1, String value2) {
            addCriterion("CODALM between", value1, value2, "codalm");
            return (Criteria) this;
        }

        public Criteria andCodalmNotBetween(String value1, String value2) {
            addCriterion("CODALM not between", value1, value2, "codalm");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaIsNull() {
            addCriterion("UID_INSTANCIA is null");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaIsNotNull() {
            addCriterion("UID_INSTANCIA is not null");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaEqualTo(String value) {
            addCriterion("UID_INSTANCIA =", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaNotEqualTo(String value) {
            addCriterion("UID_INSTANCIA <>", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaGreaterThan(String value) {
            addCriterion("UID_INSTANCIA >", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaGreaterThanOrEqualTo(String value) {
            addCriterion("UID_INSTANCIA >=", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLessThan(String value) {
            addCriterion("UID_INSTANCIA <", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLessThanOrEqualTo(String value) {
            addCriterion("UID_INSTANCIA <=", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLike(String value) {
            addCriterion("UID_INSTANCIA like", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaNotLike(String value) {
            addCriterion("UID_INSTANCIA not like", value, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaIn(List<String> values) {
            addCriterion("UID_INSTANCIA in", values, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaNotIn(List<String> values) {
            addCriterion("UID_INSTANCIA not in", values, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaBetween(String value1, String value2) {
            addCriterion("UID_INSTANCIA between", value1, value2, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaNotBetween(String value1, String value2) {
            addCriterion("UID_INSTANCIA not between", value1, value2, "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioIsNull() {
            addCriterion("ID_USUARIO is null");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioIsNotNull() {
            addCriterion("ID_USUARIO is not null");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioEqualTo(Long value) {
            addCriterion("ID_USUARIO =", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioNotEqualTo(Long value) {
            addCriterion("ID_USUARIO <>", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioGreaterThan(Long value) {
            addCriterion("ID_USUARIO >", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_USUARIO >=", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioLessThan(Long value) {
            addCriterion("ID_USUARIO <", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioLessThanOrEqualTo(Long value) {
            addCriterion("ID_USUARIO <=", value, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioIn(List<Long> values) {
            addCriterion("ID_USUARIO in", values, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioNotIn(List<Long> values) {
            addCriterion("ID_USUARIO not in", values, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioBetween(Long value1, Long value2) {
            addCriterion("ID_USUARIO between", value1, value2, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andIdUsuarioNotBetween(Long value1, Long value2) {
            addCriterion("ID_USUARIO not between", value1, value2, "idUsuario");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodalmLikeInsensitive(String value) {
            addCriterion("upper(CODALM) like", value.toUpperCase(), "codalm");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLikeInsensitive(String value) {
            addCriterion("upper(UID_INSTANCIA) like", value.toUpperCase(), "uidInstancia");
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