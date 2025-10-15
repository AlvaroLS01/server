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
package com.comerzzia.pos.persistence.core.documentos.propiedades;

import java.util.ArrayList;
import java.util.List;

public class PropiedadDocumentoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO = "ID_TIPO_DOCUMENTO";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO_DESC = "ID_TIPO_DOCUMENTO DESC";

    public static final String ORDER_BY_PROPIEDAD = "PROPIEDAD";

    public static final String ORDER_BY_PROPIEDAD_DESC = "PROPIEDAD DESC";

    public static final String ORDER_BY_VALOR = "VALOR";

    public static final String ORDER_BY_VALOR_DESC = "VALOR DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PropiedadDocumentoExample() {
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

        public Criteria andIdTipoDocumentoIsNull() {
            addCriterion("ID_TIPO_DOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoIsNotNull() {
            addCriterion("ID_TIPO_DOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO =", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <>", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoGreaterThan(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO >", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO >=", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoLessThan(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoLessThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <=", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoIn(List<Long> values) {
            addCriterion("ID_TIPO_DOCUMENTO in", values, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotIn(List<Long> values) {
            addCriterion("ID_TIPO_DOCUMENTO not in", values, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_DOCUMENTO between", value1, value2, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_DOCUMENTO not between", value1, value2, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andPropiedadIsNull() {
            addCriterion("PROPIEDAD is null");
            return (Criteria) this;
        }

        public Criteria andPropiedadIsNotNull() {
            addCriterion("PROPIEDAD is not null");
            return (Criteria) this;
        }

        public Criteria andPropiedadEqualTo(String value) {
            addCriterion("PROPIEDAD =", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadNotEqualTo(String value) {
            addCriterion("PROPIEDAD <>", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadGreaterThan(String value) {
            addCriterion("PROPIEDAD >", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadGreaterThanOrEqualTo(String value) {
            addCriterion("PROPIEDAD >=", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadLessThan(String value) {
            addCriterion("PROPIEDAD <", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadLessThanOrEqualTo(String value) {
            addCriterion("PROPIEDAD <=", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadLike(String value) {
            addCriterion("PROPIEDAD like", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadNotLike(String value) {
            addCriterion("PROPIEDAD not like", value, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadIn(List<String> values) {
            addCriterion("PROPIEDAD in", values, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadNotIn(List<String> values) {
            addCriterion("PROPIEDAD not in", values, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadBetween(String value1, String value2) {
            addCriterion("PROPIEDAD between", value1, value2, "propiedad");
            return (Criteria) this;
        }

        public Criteria andPropiedadNotBetween(String value1, String value2) {
            addCriterion("PROPIEDAD not between", value1, value2, "propiedad");
            return (Criteria) this;
        }

        public Criteria andValorIsNull() {
            addCriterion("VALOR is null");
            return (Criteria) this;
        }

        public Criteria andValorIsNotNull() {
            addCriterion("VALOR is not null");
            return (Criteria) this;
        }

        public Criteria andValorEqualTo(String value) {
            addCriterion("VALOR =", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorNotEqualTo(String value) {
            addCriterion("VALOR <>", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorGreaterThan(String value) {
            addCriterion("VALOR >", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorGreaterThanOrEqualTo(String value) {
            addCriterion("VALOR >=", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorLessThan(String value) {
            addCriterion("VALOR <", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorLessThanOrEqualTo(String value) {
            addCriterion("VALOR <=", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorLike(String value) {
            addCriterion("VALOR like", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorNotLike(String value) {
            addCriterion("VALOR not like", value, "valor");
            return (Criteria) this;
        }

        public Criteria andValorIn(List<String> values) {
            addCriterion("VALOR in", values, "valor");
            return (Criteria) this;
        }

        public Criteria andValorNotIn(List<String> values) {
            addCriterion("VALOR not in", values, "valor");
            return (Criteria) this;
        }

        public Criteria andValorBetween(String value1, String value2) {
            addCriterion("VALOR between", value1, value2, "valor");
            return (Criteria) this;
        }

        public Criteria andValorNotBetween(String value1, String value2) {
            addCriterion("VALOR not between", value1, value2, "valor");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andPropiedadLikeInsensitive(String value) {
            addCriterion("upper(PROPIEDAD) like", value.toUpperCase(), "propiedad");
            return (Criteria) this;
        }

        public Criteria andValorLikeInsensitive(String value) {
            addCriterion("upper(VALOR) like", value.toUpperCase(), "valor");
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