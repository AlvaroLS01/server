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
package com.comerzzia.pos.persistence.core.impuestos.porcentajes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PorcentajeImpuestoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS = "ID_GRUPO_IMPUESTOS";

    public static final String ORDER_BY_ID_GRUPO_IMPUESTOS_DESC = "ID_GRUPO_IMPUESTOS DESC";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS = "ID_TRAT_IMPUESTOS";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS_DESC = "ID_TRAT_IMPUESTOS DESC";

    public static final String ORDER_BY_CODIMP = "CODIMP";

    public static final String ORDER_BY_CODIMP_DESC = "CODIMP DESC";

    public static final String ORDER_BY_PORCENTAJE = "PORCENTAJE";

    public static final String ORDER_BY_PORCENTAJE_DESC = "PORCENTAJE DESC";

    public static final String ORDER_BY_PORCENTAJE_RECARGO = "PORCENTAJE_RECARGO";

    public static final String ORDER_BY_PORCENTAJE_RECARGO_DESC = "PORCENTAJE_RECARGO DESC";
    
    public static final String ORDER_BY_CODIMP_FISCAL = "CODIMP_FISCAL";
    
    public static final String ORDER_BY_CODIMP_FISCAL_DESC = "CODIMP_FISCAL DESC"; 

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PorcentajeImpuestoExample() {
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

        public Criteria andIdTratamientoImpuestosIsNull() {
            addCriterion("ID_TRAT_IMPUESTOS is null");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosIsNotNull() {
            addCriterion("ID_TRAT_IMPUESTOS is not null");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS =", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosNotEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <>", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosGreaterThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >=", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosLessThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosLessThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <=", value, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS in", values, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosNotIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS not in", values, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS between", value1, value2, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratamientoImpuestosNotBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS not between", value1, value2, "idTratamientoImpuestos");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoIsNull() {
            addCriterion("CODIMP is null");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoIsNotNull() {
            addCriterion("CODIMP is not null");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoEqualTo(String value) {
            addCriterion("CODIMP =", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoNotEqualTo(String value) {
            addCriterion("CODIMP <>", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoGreaterThan(String value) {
            addCriterion("CODIMP >", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoGreaterThanOrEqualTo(String value) {
            addCriterion("CODIMP >=", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoLessThan(String value) {
            addCriterion("CODIMP <", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoLessThanOrEqualTo(String value) {
            addCriterion("CODIMP <=", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoLike(String value) {
            addCriterion("CODIMP like", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoNotLike(String value) {
            addCriterion("CODIMP not like", value, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoIn(List<String> values) {
            addCriterion("CODIMP in", values, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoNotIn(List<String> values) {
            addCriterion("CODIMP not in", values, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoBetween(String value1, String value2) {
            addCriterion("CODIMP between", value1, value2, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoNotBetween(String value1, String value2) {
            addCriterion("CODIMP not between", value1, value2, "codImpuesto");
            return (Criteria) this;
        }

        public Criteria andPorcentajeIsNull() {
            addCriterion("PORCENTAJE is null");
            return (Criteria) this;
        }

        public Criteria andPorcentajeIsNotNull() {
            addCriterion("PORCENTAJE is not null");
            return (Criteria) this;
        }

        public Criteria andPorcentajeEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE =", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeNotEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE <>", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeGreaterThan(BigDecimal value) {
            addCriterion("PORCENTAJE >", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE >=", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeLessThan(BigDecimal value) {
            addCriterion("PORCENTAJE <", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE <=", value, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeIn(List<BigDecimal> values) {
            addCriterion("PORCENTAJE in", values, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeNotIn(List<BigDecimal> values) {
            addCriterion("PORCENTAJE not in", values, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORCENTAJE between", value1, value2, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORCENTAJE not between", value1, value2, "porcentaje");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoIsNull() {
            addCriterion("PORCENTAJE_RECARGO is null");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoIsNotNull() {
            addCriterion("PORCENTAJE_RECARGO is not null");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO =", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoNotEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO <>", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoGreaterThan(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO >", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO >=", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoLessThan(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO <", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PORCENTAJE_RECARGO <=", value, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoIn(List<BigDecimal> values) {
            addCriterion("PORCENTAJE_RECARGO in", values, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoNotIn(List<BigDecimal> values) {
            addCriterion("PORCENTAJE_RECARGO not in", values, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORCENTAJE_RECARGO between", value1, value2, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andPorcentajeRecargoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PORCENTAJE_RECARGO not between", value1, value2, "porcentajeRecargo");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodImpuestoLikeInsensitive(String value) {
            addCriterion("upper(CODIMP) like", value.toUpperCase(), "codImpuesto");
            return (Criteria) this;
        }
        
        public Criteria andCodimpFiscalIsNull() { 
         	addCriterion("CODIMP_FISCAL is null"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalIsNotNull() { 
         	addCriterion("CODIMP_FISCAL is not null"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalEqualTo(String value) { 
         	addCriterion("CODIMP_FISCAL =", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalNotEqualTo(String value) { 
         	addCriterion("CODIMP_FISCAL <>", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
         	 
        public Criteria andCodimpFiscalGreaterThan(String value) { 
         	addCriterion("CODIMP_FISCAL >", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalGreaterThanOrEqualTo(String value) { 
         	addCriterion("CODIMP_FISCAL >=", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
         	
        public Criteria andCodimpFiscalLessThan(String value) { 
         	addCriterion("CODIMP_FISCAL <", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalLessThanOrEqualTo(String value) { 
         	addCriterion("CODIMP_FISCAL <=", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalLike(String value) { 
         	addCriterion("CODIMP_FISCAL like", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
        	 
        public Criteria andCodimpFiscalNotLike(String value) { 
         	addCriterion("CODIMP_FISCAL not like", value, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
        
        public Criteria andCodimpFiscalIn(List<String> values) { 
         	addCriterion("CODIMP_FISCAL in", values, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalNotIn(List<String> values) { 
         	addCriterion("CODIMP_FISCAL not in", values, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
          
        public Criteria andCodimpFiscalBetween(String value1, String value2) { 
         	addCriterion("CODIMP_FISCAL between", value1, value2, "codimpFiscal"); 
         	return (Criteria) this; 
        } 
  
        public Criteria andCodimpFiscalNotBetween(String value1, String value2) { 
         	addCriterion("CODIMP_FISCAL not between", value1, value2, "codimpFiscal"); 
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