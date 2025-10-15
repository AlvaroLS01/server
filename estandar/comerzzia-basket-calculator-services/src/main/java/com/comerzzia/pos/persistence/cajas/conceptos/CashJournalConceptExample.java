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
package com.comerzzia.pos.persistence.cajas.conceptos;

import java.util.ArrayList;
import java.util.List;

public class CashJournalConceptExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODCONCEPTO_MOV = "CODCONCEPTO_MOV";

    public static final String ORDER_BY_CODCONCEPTO_MOV_DESC = "CODCONCEPTO_MOV DESC";

    public static final String ORDER_BY_DESCONCEPTO_MOV = "DESCONCEPTO_MOV";

    public static final String ORDER_BY_DESCONCEPTO_MOV_DESC = "DESCONCEPTO_MOV DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_MANUAL = "MANUAL";

    public static final String ORDER_BY_MANUAL_DESC = "MANUAL DESC";
    
    public static final String ORDER_BY_IN_OUT = "IN_OUT";

    public static final String ORDER_BY_IN_OUT_DESC = "IN_OUT DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CashJournalConceptExample() {
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

        protected List<Criterion> manualCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
            manualCriteria = new ArrayList<Criterion>();
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

        public List<Criterion> getManualCriteria() {
            return manualCriteria;
        }

        protected void addManualCriterion(String condition, Object value, String property) {
            if (value != null) {
                manualCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addManualCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                manualCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || activoCriteria.size() > 0
                || manualCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(activoCriteria);
                allCriteria.addAll(manualCriteria);
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

        public Criteria andCodConceptoMovimientoIsNull() {
            addCriterion("CODCONCEPTO_MOV is null");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoIsNotNull() {
            addCriterion("CODCONCEPTO_MOV is not null");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoEqualTo(String value) {
            addCriterion("CODCONCEPTO_MOV =", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoNotEqualTo(String value) {
            addCriterion("CODCONCEPTO_MOV <>", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoGreaterThan(String value) {
            addCriterion("CODCONCEPTO_MOV >", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoGreaterThanOrEqualTo(String value) {
            addCriterion("CODCONCEPTO_MOV >=", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoLessThan(String value) {
            addCriterion("CODCONCEPTO_MOV <", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoLessThanOrEqualTo(String value) {
            addCriterion("CODCONCEPTO_MOV <=", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoLike(String value) {
            addCriterion("CODCONCEPTO_MOV like", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoNotLike(String value) {
            addCriterion("CODCONCEPTO_MOV not like", value, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoIn(List<String> values) {
            addCriterion("CODCONCEPTO_MOV in", values, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoNotIn(List<String> values) {
            addCriterion("CODCONCEPTO_MOV not in", values, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoBetween(String value1, String value2) {
            addCriterion("CODCONCEPTO_MOV between", value1, value2, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoNotBetween(String value1, String value2) {
            addCriterion("CODCONCEPTO_MOV not between", value1, value2, "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoIsNull() {
            addCriterion("DESCONCEPTO_MOV is null");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoIsNotNull() {
            addCriterion("DESCONCEPTO_MOV is not null");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoEqualTo(String value) {
            addCriterion("DESCONCEPTO_MOV =", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoNotEqualTo(String value) {
            addCriterion("DESCONCEPTO_MOV <>", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoGreaterThan(String value) {
            addCriterion("DESCONCEPTO_MOV >", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoGreaterThanOrEqualTo(String value) {
            addCriterion("DESCONCEPTO_MOV >=", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoLessThan(String value) {
            addCriterion("DESCONCEPTO_MOV <", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoLessThanOrEqualTo(String value) {
            addCriterion("DESCONCEPTO_MOV <=", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoLike(String value) {
            addCriterion("DESCONCEPTO_MOV like", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoNotLike(String value) {
            addCriterion("DESCONCEPTO_MOV not like", value, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoIn(List<String> values) {
            addCriterion("DESCONCEPTO_MOV in", values, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoNotIn(List<String> values) {
            addCriterion("DESCONCEPTO_MOV not in", values, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoBetween(String value1, String value2) {
            addCriterion("DESCONCEPTO_MOV between", value1, value2, "desConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoNotBetween(String value1, String value2) {
            addCriterion("DESCONCEPTO_MOV not between", value1, value2, "desConceptoMovimiento");
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

        public Criteria andManualIsNull() {
            addCriterion("MANUAL is null");
            return (Criteria) this;
        }

        public Criteria andManualIsNotNull() {
            addCriterion("MANUAL is not null");
            return (Criteria) this;
        }

        public Criteria andManualEqualTo(Boolean value) {
            addManualCriterion("MANUAL =", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotEqualTo(Boolean value) {
            addManualCriterion("MANUAL <>", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualGreaterThan(Boolean value) {
            addManualCriterion("MANUAL >", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualGreaterThanOrEqualTo(Boolean value) {
            addManualCriterion("MANUAL >=", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLessThan(Boolean value) {
            addManualCriterion("MANUAL <", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLessThanOrEqualTo(Boolean value) {
            addManualCriterion("MANUAL <=", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLike(Boolean value) {
            addManualCriterion("MANUAL like", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotLike(Boolean value) {
            addManualCriterion("MANUAL not like", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualIn(List<Boolean> values) {
            addManualCriterion("MANUAL in", values, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotIn(List<Boolean> values) {
            addManualCriterion("MANUAL not in", values, "manual");
            return (Criteria) this;
        }

        public Criteria andManualBetween(Boolean value1, Boolean value2) {
            addManualCriterion("MANUAL between", value1, value2, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotBetween(Boolean value1, Boolean value2) {
            addManualCriterion("MANUAL not between", value1, value2, "manual");
            return (Criteria) this;
        }
        
        public Criteria andInOutIsNull() {
            addCriterion("IN_OUT is null");
            return (Criteria) this;
        }

        public Criteria andInOutIsNotNull() {
            addCriterion("IN_OUT is not null");
            return (Criteria) this;
        }

        public Criteria andInOutEqualTo(String value) {
            addCriterion("IN_OUT =", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutNotEqualTo(String value) {
            addCriterion("IN_OUT <>", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutGreaterThan(String value) {
            addCriterion("IN_OUT >", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutGreaterThanOrEqualTo(String value) {
            addCriterion("IN_OUT >=", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutLessThan(String value) {
            addCriterion("IN_OUT <", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutLessThanOrEqualTo(String value) {
            addCriterion("IN_OUT <=", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutLike(String value) {
            addCriterion("IN_OUT like", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutNotLike(String value) {
            addCriterion("IN_OUT not like", value, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutIn(List<String> values) {
            addCriterion("IN_OUT in", values, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutNotIn(List<String> values) {
            addCriterion("IN_OUT not in", values, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutBetween(String value1, String value2) {
            addCriterion("IN_OUT between", value1, value2, "inOut");
            return (Criteria) this;
        }

        public Criteria andInOutNotBetween(String value1, String value2) {
            addCriterion("IN_OUT not between", value1, value2, "inOut");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoLikeInsensitive(String value) {
            addCriterion("upper(CODCONCEPTO_MOV) like", value.toUpperCase(), "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andDesConceptoMovimientoLikeInsensitive(String value) {
            addCriterion("upper(DESCONCEPTO_MOV) like", value.toUpperCase(), "desConceptoMovimiento");
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