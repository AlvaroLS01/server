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
package com.comerzzia.pos.persistence.core.impuestos.tratamientos;

import java.util.ArrayList;
import java.util.List;

public class TratamientoImpuestoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS = "ID_TRAT_IMPUESTOS";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS_DESC = "ID_TRAT_IMPUESTOS DESC";

    public static final String ORDER_BY_CODTRATIMP = "CODTRATIMP";

    public static final String ORDER_BY_CODTRATIMP_DESC = "CODTRATIMP DESC";

    public static final String ORDER_BY_DESTRATIMP = "DESTRATIMP";

    public static final String ORDER_BY_DESTRATIMP_DESC = "DESTRATIMP DESC";

    public static final String ORDER_BY_APLICA_RECARGO = "APLICA_RECARGO";

    public static final String ORDER_BY_APLICA_RECARGO_DESC = "APLICA_RECARGO DESC";

    public static final String ORDER_BY_CODPAIS = "CODPAIS";

    public static final String ORDER_BY_CODPAIS_DESC = "CODPAIS DESC";

    public static final String ORDER_BY_REGION_IMPUESTOS = "REGION_IMPUESTOS";

    public static final String ORDER_BY_REGION_IMPUESTOS_DESC = "REGION_IMPUESTOS DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TratamientoImpuestoExample() {
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

        public Criteria andCodtratimpIsNull() {
            addCriterion("CODTRATIMP is null");
            return (Criteria) this;
        }

        public Criteria andCodtratimpIsNotNull() {
            addCriterion("CODTRATIMP is not null");
            return (Criteria) this;
        }

        public Criteria andCodtratimpEqualTo(String value) {
            addCriterion("CODTRATIMP =", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpNotEqualTo(String value) {
            addCriterion("CODTRATIMP <>", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpGreaterThan(String value) {
            addCriterion("CODTRATIMP >", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpGreaterThanOrEqualTo(String value) {
            addCriterion("CODTRATIMP >=", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpLessThan(String value) {
            addCriterion("CODTRATIMP <", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpLessThanOrEqualTo(String value) {
            addCriterion("CODTRATIMP <=", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpLike(String value) {
            addCriterion("CODTRATIMP like", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpNotLike(String value) {
            addCriterion("CODTRATIMP not like", value, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpIn(List<String> values) {
            addCriterion("CODTRATIMP in", values, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpNotIn(List<String> values) {
            addCriterion("CODTRATIMP not in", values, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpBetween(String value1, String value2) {
            addCriterion("CODTRATIMP between", value1, value2, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andCodtratimpNotBetween(String value1, String value2) {
            addCriterion("CODTRATIMP not between", value1, value2, "codtratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpIsNull() {
            addCriterion("DESTRATIMP is null");
            return (Criteria) this;
        }

        public Criteria andDestratimpIsNotNull() {
            addCriterion("DESTRATIMP is not null");
            return (Criteria) this;
        }

        public Criteria andDestratimpEqualTo(String value) {
            addCriterion("DESTRATIMP =", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpNotEqualTo(String value) {
            addCriterion("DESTRATIMP <>", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpGreaterThan(String value) {
            addCriterion("DESTRATIMP >", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpGreaterThanOrEqualTo(String value) {
            addCriterion("DESTRATIMP >=", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpLessThan(String value) {
            addCriterion("DESTRATIMP <", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpLessThanOrEqualTo(String value) {
            addCriterion("DESTRATIMP <=", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpLike(String value) {
            addCriterion("DESTRATIMP like", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpNotLike(String value) {
            addCriterion("DESTRATIMP not like", value, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpIn(List<String> values) {
            addCriterion("DESTRATIMP in", values, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpNotIn(List<String> values) {
            addCriterion("DESTRATIMP not in", values, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpBetween(String value1, String value2) {
            addCriterion("DESTRATIMP between", value1, value2, "destratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpNotBetween(String value1, String value2) {
            addCriterion("DESTRATIMP not between", value1, value2, "destratimp");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoIsNull() {
            addCriterion("APLICA_RECARGO is null");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoIsNotNull() {
            addCriterion("APLICA_RECARGO is not null");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoEqualTo(String value) {
            addCriterion("APLICA_RECARGO =", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoNotEqualTo(String value) {
            addCriterion("APLICA_RECARGO <>", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoGreaterThan(String value) {
            addCriterion("APLICA_RECARGO >", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoGreaterThanOrEqualTo(String value) {
            addCriterion("APLICA_RECARGO >=", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoLessThan(String value) {
            addCriterion("APLICA_RECARGO <", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoLessThanOrEqualTo(String value) {
            addCriterion("APLICA_RECARGO <=", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoLike(String value) {
            addCriterion("APLICA_RECARGO like", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoNotLike(String value) {
            addCriterion("APLICA_RECARGO not like", value, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoIn(List<String> values) {
            addCriterion("APLICA_RECARGO in", values, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoNotIn(List<String> values) {
            addCriterion("APLICA_RECARGO not in", values, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoBetween(String value1, String value2) {
            addCriterion("APLICA_RECARGO between", value1, value2, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoNotBetween(String value1, String value2) {
            addCriterion("APLICA_RECARGO not between", value1, value2, "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andCodpaisIsNull() {
            addCriterion("CODPAIS is null");
            return (Criteria) this;
        }

        public Criteria andCodpaisIsNotNull() {
            addCriterion("CODPAIS is not null");
            return (Criteria) this;
        }

        public Criteria andCodpaisEqualTo(String value) {
            addCriterion("CODPAIS =", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotEqualTo(String value) {
            addCriterion("CODPAIS <>", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisGreaterThan(String value) {
            addCriterion("CODPAIS >", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisGreaterThanOrEqualTo(String value) {
            addCriterion("CODPAIS >=", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLessThan(String value) {
            addCriterion("CODPAIS <", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLessThanOrEqualTo(String value) {
            addCriterion("CODPAIS <=", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLike(String value) {
            addCriterion("CODPAIS like", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotLike(String value) {
            addCriterion("CODPAIS not like", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisIn(List<String> values) {
            addCriterion("CODPAIS in", values, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotIn(List<String> values) {
            addCriterion("CODPAIS not in", values, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisBetween(String value1, String value2) {
            addCriterion("CODPAIS between", value1, value2, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotBetween(String value1, String value2) {
            addCriterion("CODPAIS not between", value1, value2, "codpais");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosIsNull() {
            addCriterion("REGION_IMPUESTOS is null");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosIsNotNull() {
            addCriterion("REGION_IMPUESTOS is not null");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosEqualTo(String value) {
            addCriterion("REGION_IMPUESTOS =", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosNotEqualTo(String value) {
            addCriterion("REGION_IMPUESTOS <>", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosGreaterThan(String value) {
            addCriterion("REGION_IMPUESTOS >", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosGreaterThanOrEqualTo(String value) {
            addCriterion("REGION_IMPUESTOS >=", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosLessThan(String value) {
            addCriterion("REGION_IMPUESTOS <", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosLessThanOrEqualTo(String value) {
            addCriterion("REGION_IMPUESTOS <=", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosLike(String value) {
            addCriterion("REGION_IMPUESTOS like", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosNotLike(String value) {
            addCriterion("REGION_IMPUESTOS not like", value, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosIn(List<String> values) {
            addCriterion("REGION_IMPUESTOS in", values, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosNotIn(List<String> values) {
            addCriterion("REGION_IMPUESTOS not in", values, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosBetween(String value1, String value2) {
            addCriterion("REGION_IMPUESTOS between", value1, value2, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosNotBetween(String value1, String value2) {
            addCriterion("REGION_IMPUESTOS not between", value1, value2, "regionImpuestos");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodtratimpLikeInsensitive(String value) {
            addCriterion("upper(CODTRATIMP) like", value.toUpperCase(), "codtratimp");
            return (Criteria) this;
        }

        public Criteria andDestratimpLikeInsensitive(String value) {
            addCriterion("upper(DESTRATIMP) like", value.toUpperCase(), "destratimp");
            return (Criteria) this;
        }

        public Criteria andAplicaRecargoLikeInsensitive(String value) {
            addCriterion("upper(APLICA_RECARGO) like", value.toUpperCase(), "aplicaRecargo");
            return (Criteria) this;
        }

        public Criteria andCodpaisLikeInsensitive(String value) {
            addCriterion("upper(CODPAIS) like", value.toUpperCase(), "codpais");
            return (Criteria) this;
        }

        public Criteria andRegionImpuestosLikeInsensitive(String value) {
            addCriterion("upper(REGION_IMPUESTOS) like", value.toUpperCase(), "regionImpuestos");
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