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
package com.comerzzia.pos.persistence.core.config.configcontadores.parametros;

import java.util.ArrayList;
import java.util.List;

public class ConfigContadorParametroExample {
    public static final String ORDER_BY_ID_CONTADOR = "ID_CONTADOR";

    public static final String ORDER_BY_ID_CONTADOR_DESC = "ID_CONTADOR DESC";

    public static final String ORDER_BY_PARAMETRO = "PARAMETRO";

    public static final String ORDER_BY_PARAMETRO_DESC = "PARAMETRO DESC";

    public static final String ORDER_BY_LONGITUD_MAXIMA = "LONGITUD_MAXIMA";

    public static final String ORDER_BY_LONGITUD_MAXIMA_DESC = "LONGITUD_MAXIMA DESC";

    public static final String ORDER_BY_DIRECCION_RELLENO = "DIRECCION_RELLENO";

    public static final String ORDER_BY_DIRECCION_RELLENO_DESC = "DIRECCION_RELLENO DESC";

    public static final String ORDER_BY_CARACTER_RELLENO = "CARACTER_RELLENO";

    public static final String ORDER_BY_CARACTER_RELLENO_DESC = "CARACTER_RELLENO DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConfigContadorParametroExample() {
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

        public Criteria andIdContadorIsNull() {
            addCriterion("ID_CONTADOR is null");
            return (Criteria) this;
        }

        public Criteria andIdContadorIsNotNull() {
            addCriterion("ID_CONTADOR is not null");
            return (Criteria) this;
        }

        public Criteria andIdContadorEqualTo(String value) {
            addCriterion("ID_CONTADOR =", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotEqualTo(String value) {
            addCriterion("ID_CONTADOR <>", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorGreaterThan(String value) {
            addCriterion("ID_CONTADOR >", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorGreaterThanOrEqualTo(String value) {
            addCriterion("ID_CONTADOR >=", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLessThan(String value) {
            addCriterion("ID_CONTADOR <", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLessThanOrEqualTo(String value) {
            addCriterion("ID_CONTADOR <=", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLike(String value) {
            addCriterion("ID_CONTADOR like", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotLike(String value) {
            addCriterion("ID_CONTADOR not like", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorIn(List<String> values) {
            addCriterion("ID_CONTADOR in", values, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotIn(List<String> values) {
            addCriterion("ID_CONTADOR not in", values, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorBetween(String value1, String value2) {
            addCriterion("ID_CONTADOR between", value1, value2, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotBetween(String value1, String value2) {
            addCriterion("ID_CONTADOR not between", value1, value2, "idContador");
            return (Criteria) this;
        }

        public Criteria andParametroIsNull() {
            addCriterion("PARAMETRO is null");
            return (Criteria) this;
        }

        public Criteria andParametroIsNotNull() {
            addCriterion("PARAMETRO is not null");
            return (Criteria) this;
        }

        public Criteria andParametroEqualTo(String value) {
            addCriterion("PARAMETRO =", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroNotEqualTo(String value) {
            addCriterion("PARAMETRO <>", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroGreaterThan(String value) {
            addCriterion("PARAMETRO >", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroGreaterThanOrEqualTo(String value) {
            addCriterion("PARAMETRO >=", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroLessThan(String value) {
            addCriterion("PARAMETRO <", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroLessThanOrEqualTo(String value) {
            addCriterion("PARAMETRO <=", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroLike(String value) {
            addCriterion("PARAMETRO like", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroNotLike(String value) {
            addCriterion("PARAMETRO not like", value, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroIn(List<String> values) {
            addCriterion("PARAMETRO in", values, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroNotIn(List<String> values) {
            addCriterion("PARAMETRO not in", values, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroBetween(String value1, String value2) {
            addCriterion("PARAMETRO between", value1, value2, "parametro");
            return (Criteria) this;
        }

        public Criteria andParametroNotBetween(String value1, String value2) {
            addCriterion("PARAMETRO not between", value1, value2, "parametro");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaIsNull() {
            addCriterion("LONGITUD_MAXIMA is null");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaIsNotNull() {
            addCriterion("LONGITUD_MAXIMA is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaEqualTo(Short value) {
            addCriterion("LONGITUD_MAXIMA =", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaNotEqualTo(Short value) {
            addCriterion("LONGITUD_MAXIMA <>", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaGreaterThan(Short value) {
            addCriterion("LONGITUD_MAXIMA >", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaGreaterThanOrEqualTo(Short value) {
            addCriterion("LONGITUD_MAXIMA >=", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaLessThan(Short value) {
            addCriterion("LONGITUD_MAXIMA <", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaLessThanOrEqualTo(Short value) {
            addCriterion("LONGITUD_MAXIMA <=", value, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaIn(List<Short> values) {
            addCriterion("LONGITUD_MAXIMA in", values, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaNotIn(List<Short> values) {
            addCriterion("LONGITUD_MAXIMA not in", values, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaBetween(Short value1, Short value2) {
            addCriterion("LONGITUD_MAXIMA between", value1, value2, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andLongitudMaximaNotBetween(Short value1, Short value2) {
            addCriterion("LONGITUD_MAXIMA not between", value1, value2, "longitudMaxima");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoIsNull() {
            addCriterion("DIRECCION_RELLENO is null");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoIsNotNull() {
            addCriterion("DIRECCION_RELLENO is not null");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoEqualTo(String value) {
            addCriterion("DIRECCION_RELLENO =", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoNotEqualTo(String value) {
            addCriterion("DIRECCION_RELLENO <>", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoGreaterThan(String value) {
            addCriterion("DIRECCION_RELLENO >", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoGreaterThanOrEqualTo(String value) {
            addCriterion("DIRECCION_RELLENO >=", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoLessThan(String value) {
            addCriterion("DIRECCION_RELLENO <", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoLessThanOrEqualTo(String value) {
            addCriterion("DIRECCION_RELLENO <=", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoLike(String value) {
            addCriterion("DIRECCION_RELLENO like", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoNotLike(String value) {
            addCriterion("DIRECCION_RELLENO not like", value, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoIn(List<String> values) {
            addCriterion("DIRECCION_RELLENO in", values, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoNotIn(List<String> values) {
            addCriterion("DIRECCION_RELLENO not in", values, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoBetween(String value1, String value2) {
            addCriterion("DIRECCION_RELLENO between", value1, value2, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoNotBetween(String value1, String value2) {
            addCriterion("DIRECCION_RELLENO not between", value1, value2, "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoIsNull() {
            addCriterion("CARACTER_RELLENO is null");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoIsNotNull() {
            addCriterion("CARACTER_RELLENO is not null");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoEqualTo(String value) {
            addCriterion("CARACTER_RELLENO =", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoNotEqualTo(String value) {
            addCriterion("CARACTER_RELLENO <>", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoGreaterThan(String value) {
            addCriterion("CARACTER_RELLENO >", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoGreaterThanOrEqualTo(String value) {
            addCriterion("CARACTER_RELLENO >=", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoLessThan(String value) {
            addCriterion("CARACTER_RELLENO <", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoLessThanOrEqualTo(String value) {
            addCriterion("CARACTER_RELLENO <=", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoLike(String value) {
            addCriterion("CARACTER_RELLENO like", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoNotLike(String value) {
            addCriterion("CARACTER_RELLENO not like", value, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoIn(List<String> values) {
            addCriterion("CARACTER_RELLENO in", values, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoNotIn(List<String> values) {
            addCriterion("CARACTER_RELLENO not in", values, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoBetween(String value1, String value2) {
            addCriterion("CARACTER_RELLENO between", value1, value2, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoNotBetween(String value1, String value2) {
            addCriterion("CARACTER_RELLENO not between", value1, value2, "caracterRelleno");
            return (Criteria) this;
        }

        public Criteria andIdContadorLikeInsensitive(String value) {
            addCriterion("upper(ID_CONTADOR) like", value.toUpperCase(), "idContador");
            return (Criteria) this;
        }

        public Criteria andParametroLikeInsensitive(String value) {
            addCriterion("upper(PARAMETRO) like", value.toUpperCase(), "parametro");
            return (Criteria) this;
        }

        public Criteria andDireccionRellenoLikeInsensitive(String value) {
            addCriterion("upper(DIRECCION_RELLENO) like", value.toUpperCase(), "direccionRelleno");
            return (Criteria) this;
        }

        public Criteria andCaracterRellenoLikeInsensitive(String value) {
            addCriterion("upper(CARACTER_RELLENO) like", value.toUpperCase(), "caracterRelleno");
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