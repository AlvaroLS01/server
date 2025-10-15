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
package com.comerzzia.pos.persistence.core.impuestos.grupos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrupoImpuestoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS = "ID_GRUPO_IMPUESTOS";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS_DESC = "ID_GRUPO_IMPUESTOS DESC";

    public static final String ORDER_BY_VIGENCIA_DESDE = "VIGENCIA_DESDE";

    public static final String ORDER_BY_VIGENCIA_DESDE_DESC = "VIGENCIA_DESDE DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GrupoImpuestoExample() {
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

        public Criteria andVigenciaDesdeIsNull() {
            addCriterion("VIGENCIA_DESDE is null");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeIsNotNull() {
            addCriterion("VIGENCIA_DESDE is not null");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeEqualTo(Date value) {
            addCriterion("VIGENCIA_DESDE =", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeNotEqualTo(Date value) {
            addCriterion("VIGENCIA_DESDE <>", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeGreaterThan(Date value) {
            addCriterion("VIGENCIA_DESDE >", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeGreaterThanOrEqualTo(Date value) {
            addCriterion("VIGENCIA_DESDE >=", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeLessThan(Date value) {
            addCriterion("VIGENCIA_DESDE <", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeLessThanOrEqualTo(Date value) {
            addCriterion("VIGENCIA_DESDE <=", value, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeIn(List<Date> values) {
            addCriterion("VIGENCIA_DESDE in", values, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeNotIn(List<Date> values) {
            addCriterion("VIGENCIA_DESDE not in", values, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeBetween(Date value1, Date value2) {
            addCriterion("VIGENCIA_DESDE between", value1, value2, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andVigenciaDesdeNotBetween(Date value1, Date value2) {
            addCriterion("VIGENCIA_DESDE not between", value1, value2, "vigenciaDesde");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
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