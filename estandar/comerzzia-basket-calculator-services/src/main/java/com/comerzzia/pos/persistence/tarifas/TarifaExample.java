package com.comerzzia.pos.persistence.tarifas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TarifaExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODTAR = "CODTAR";

    public static final String ORDER_BY_CODTAR_DESC = "CODTAR DESC";

    public static final String ORDER_BY_DESTAR = "DESTAR";

    public static final String ORDER_BY_DESTAR_DESC = "DESTAR DESC";

    public static final String ORDER_BY_VERSION = "VERSION";

    public static final String ORDER_BY_VERSION_DESC = "VERSION DESC";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS = "ID_GRUPO_IMPUESTOS";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS_DESC = "ID_GRUPO_IMPUESTOS DESC";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS = "ID_TRAT_IMPUESTOS";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS_DESC = "ID_TRAT_IMPUESTOS DESC";

    public static final String ORDER_BY_FECHA_VERSION = "FECHA_VERSION";

    public static final String ORDER_BY_FECHA_VERSION_DESC = "FECHA_VERSION DESC";

    public static final String ORDER_BY_CODTAR_PADRE = "CODTAR_PADRE";

    public static final String ORDER_BY_CODTAR_PADRE_DESC = "CODTAR_PADRE DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TarifaExample() {
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

        public Criteria andDesTarifaIsNull() {
            addCriterion("DESTAR is null");
            return (Criteria) this;
        }

        public Criteria andDesTarifaIsNotNull() {
            addCriterion("DESTAR is not null");
            return (Criteria) this;
        }

        public Criteria andDesTarifaEqualTo(String value) {
            addCriterion("DESTAR =", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaNotEqualTo(String value) {
            addCriterion("DESTAR <>", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaGreaterThan(String value) {
            addCriterion("DESTAR >", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaGreaterThanOrEqualTo(String value) {
            addCriterion("DESTAR >=", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaLessThan(String value) {
            addCriterion("DESTAR <", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaLessThanOrEqualTo(String value) {
            addCriterion("DESTAR <=", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaLike(String value) {
            addCriterion("DESTAR like", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaNotLike(String value) {
            addCriterion("DESTAR not like", value, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaIn(List<String> values) {
            addCriterion("DESTAR in", values, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaNotIn(List<String> values) {
            addCriterion("DESTAR not in", values, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaBetween(String value1, String value2) {
            addCriterion("DESTAR between", value1, value2, "desTarifa");
            return (Criteria) this;
        }

        public Criteria andDesTarifaNotBetween(String value1, String value2) {
            addCriterion("DESTAR not between", value1, value2, "desTarifa");
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

        public Criteria andIdGrupoImpuestosIsNull() {
            addCriterion("ID_GRUPO_IMPUESTOS is null");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosIsNotNull() {
            addCriterion("ID_GRUPO_IMPUESTOS is not null");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosEqualTo(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS =", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosNotEqualTo(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS <>", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosGreaterThan(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS >", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS >=", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosLessThan(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS <", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosLessThanOrEqualTo(Integer value) {
            addCriterion("ID_GRUPO_IMPUESTOS <=", value, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosIn(List<Integer> values) {
            addCriterion("ID_GRUPO_IMPUESTOS in", values, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosNotIn(List<Integer> values) {
            addCriterion("ID_GRUPO_IMPUESTOS not in", values, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosBetween(Integer value1, Integer value2) {
            addCriterion("ID_GRUPO_IMPUESTOS between", value1, value2, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdGrupoImpuestosNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_GRUPO_IMPUESTOS not between", value1, value2, "idGrupoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIsNull() {
            addCriterion("ID_TRAT_IMPUESTOS is null");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIsNotNull() {
            addCriterion("ID_TRAT_IMPUESTOS is not null");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS =", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <>", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosGreaterThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >=", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosLessThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosLessThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <=", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS in", values, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS not in", values, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS between", value1, value2, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS not between", value1, value2, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionIsNull() {
            addCriterion("FECHA_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionIsNotNull() {
            addCriterion("FECHA_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionEqualTo(Date value) {
            addCriterion("FECHA_VERSION =", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionNotEqualTo(Date value) {
            addCriterion("FECHA_VERSION <>", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionGreaterThan(Date value) {
            addCriterion("FECHA_VERSION >", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION >=", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionLessThan(Date value) {
            addCriterion("FECHA_VERSION <", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION <=", value, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionIn(List<Date> values) {
            addCriterion("FECHA_VERSION in", values, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionNotIn(List<Date> values) {
            addCriterion("FECHA_VERSION not in", values, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION between", value1, value2, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andFechaVersionNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION not between", value1, value2, "fechaVersion");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreIsNull() {
            addCriterion("CODTAR_PADRE is null");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreIsNotNull() {
            addCriterion("CODTAR_PADRE is not null");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreEqualTo(String value) {
            addCriterion("CODTAR_PADRE =", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreNotEqualTo(String value) {
            addCriterion("CODTAR_PADRE <>", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreGreaterThan(String value) {
            addCriterion("CODTAR_PADRE >", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreGreaterThanOrEqualTo(String value) {
            addCriterion("CODTAR_PADRE >=", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreLessThan(String value) {
            addCriterion("CODTAR_PADRE <", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreLessThanOrEqualTo(String value) {
            addCriterion("CODTAR_PADRE <=", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreLike(String value) {
            addCriterion("CODTAR_PADRE like", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreNotLike(String value) {
            addCriterion("CODTAR_PADRE not like", value, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreIn(List<String> values) {
            addCriterion("CODTAR_PADRE in", values, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreNotIn(List<String> values) {
            addCriterion("CODTAR_PADRE not in", values, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreBetween(String value1, String value2) {
            addCriterion("CODTAR_PADRE between", value1, value2, "codTarifaPadre");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreNotBetween(String value1, String value2) {
            addCriterion("CODTAR_PADRE not between", value1, value2, "codTarifaPadre");
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

        public Criteria andDesTarifaLikeInsensitive(String value) {
            addCriterion("upper(DESTAR) like", value.toUpperCase(), "desTarifa");
            return (Criteria) this;
        }

        public Criteria andCodTarifaPadreLikeInsensitive(String value) {
            addCriterion("upper(CODTAR_PADRE) like", value.toUpperCase(), "codTarifaPadre");
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