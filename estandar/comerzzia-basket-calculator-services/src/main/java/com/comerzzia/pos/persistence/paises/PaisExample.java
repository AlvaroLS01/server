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
package com.comerzzia.pos.persistence.paises;

import java.util.ArrayList;
import java.util.List;

public class PaisExample {
    public static final String ORDER_BY_UID_INSTANCIA = "UID_INSTANCIA";

    public static final String ORDER_BY_UID_INSTANCIA_DESC = "UID_INSTANCIA DESC";

    public static final String ORDER_BY_CODPAIS = "CODPAIS";

    public static final String ORDER_BY_CODPAIS_DESC = "CODPAIS DESC";

    public static final String ORDER_BY_DESPAIS = "DESPAIS";

    public static final String ORDER_BY_DESPAIS_DESC = "DESPAIS DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_CODDIVISA = "CODDIVISA";

    public static final String ORDER_BY_CODDIVISA_DESC = "CODDIVISA DESC";

    public static final String ORDER_BY_CODLENGUA_OFICIAL = "CODLENGUA_OFICIAL";

    public static final String ORDER_BY_CODLENGUA_OFICIAL_DESC = "CODLENGUA_OFICIAL DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PaisExample() {
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

        public Criteria andCodPaisIsNull() {
            addCriterion("CODPAIS is null");
            return (Criteria) this;
        }

        public Criteria andCodPaisIsNotNull() {
            addCriterion("CODPAIS is not null");
            return (Criteria) this;
        }

        public Criteria andCodPaisEqualTo(String value) {
            addCriterion("CODPAIS =", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisNotEqualTo(String value) {
            addCriterion("CODPAIS <>", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisGreaterThan(String value) {
            addCriterion("CODPAIS >", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisGreaterThanOrEqualTo(String value) {
            addCriterion("CODPAIS >=", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisLessThan(String value) {
            addCriterion("CODPAIS <", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisLessThanOrEqualTo(String value) {
            addCriterion("CODPAIS <=", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisLike(String value) {
            addCriterion("CODPAIS like", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisNotLike(String value) {
            addCriterion("CODPAIS not like", value, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisIn(List<String> values) {
            addCriterion("CODPAIS in", values, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisNotIn(List<String> values) {
            addCriterion("CODPAIS not in", values, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisBetween(String value1, String value2) {
            addCriterion("CODPAIS between", value1, value2, "codPais");
            return (Criteria) this;
        }

        public Criteria andCodPaisNotBetween(String value1, String value2) {
            addCriterion("CODPAIS not between", value1, value2, "codPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisIsNull() {
            addCriterion("DESPAIS is null");
            return (Criteria) this;
        }

        public Criteria andDesPaisIsNotNull() {
            addCriterion("DESPAIS is not null");
            return (Criteria) this;
        }

        public Criteria andDesPaisEqualTo(String value) {
            addCriterion("DESPAIS =", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisNotEqualTo(String value) {
            addCriterion("DESPAIS <>", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisGreaterThan(String value) {
            addCriterion("DESPAIS >", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisGreaterThanOrEqualTo(String value) {
            addCriterion("DESPAIS >=", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisLessThan(String value) {
            addCriterion("DESPAIS <", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisLessThanOrEqualTo(String value) {
            addCriterion("DESPAIS <=", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisLike(String value) {
            addCriterion("DESPAIS like", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisNotLike(String value) {
            addCriterion("DESPAIS not like", value, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisIn(List<String> values) {
            addCriterion("DESPAIS in", values, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisNotIn(List<String> values) {
            addCriterion("DESPAIS not in", values, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisBetween(String value1, String value2) {
            addCriterion("DESPAIS between", value1, value2, "desPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisNotBetween(String value1, String value2) {
            addCriterion("DESPAIS not between", value1, value2, "desPais");
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

        public Criteria andCodDivisaIsNull() {
            addCriterion("CODDIVISA is null");
            return (Criteria) this;
        }

        public Criteria andCodDivisaIsNotNull() {
            addCriterion("CODDIVISA is not null");
            return (Criteria) this;
        }

        public Criteria andCodDivisaEqualTo(String value) {
            addCriterion("CODDIVISA =", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaNotEqualTo(String value) {
            addCriterion("CODDIVISA <>", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaGreaterThan(String value) {
            addCriterion("CODDIVISA >", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaGreaterThanOrEqualTo(String value) {
            addCriterion("CODDIVISA >=", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaLessThan(String value) {
            addCriterion("CODDIVISA <", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaLessThanOrEqualTo(String value) {
            addCriterion("CODDIVISA <=", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaLike(String value) {
            addCriterion("CODDIVISA like", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaNotLike(String value) {
            addCriterion("CODDIVISA not like", value, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaIn(List<String> values) {
            addCriterion("CODDIVISA in", values, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaNotIn(List<String> values) {
            addCriterion("CODDIVISA not in", values, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaBetween(String value1, String value2) {
            addCriterion("CODDIVISA between", value1, value2, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodDivisaNotBetween(String value1, String value2) {
            addCriterion("CODDIVISA not between", value1, value2, "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodLenguaIsNull() {
            addCriterion("CODLENGUA_OFICIAL is null");
            return (Criteria) this;
        }

        public Criteria andCodLenguaIsNotNull() {
            addCriterion("CODLENGUA_OFICIAL is not null");
            return (Criteria) this;
        }

        public Criteria andCodLenguaEqualTo(String value) {
            addCriterion("CODLENGUA_OFICIAL =", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaNotEqualTo(String value) {
            addCriterion("CODLENGUA_OFICIAL <>", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaGreaterThan(String value) {
            addCriterion("CODLENGUA_OFICIAL >", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaGreaterThanOrEqualTo(String value) {
            addCriterion("CODLENGUA_OFICIAL >=", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaLessThan(String value) {
            addCriterion("CODLENGUA_OFICIAL <", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaLessThanOrEqualTo(String value) {
            addCriterion("CODLENGUA_OFICIAL <=", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaLike(String value) {
            addCriterion("CODLENGUA_OFICIAL like", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaNotLike(String value) {
            addCriterion("CODLENGUA_OFICIAL not like", value, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaIn(List<String> values) {
            addCriterion("CODLENGUA_OFICIAL in", values, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaNotIn(List<String> values) {
            addCriterion("CODLENGUA_OFICIAL not in", values, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaBetween(String value1, String value2) {
            addCriterion("CODLENGUA_OFICIAL between", value1, value2, "codLengua");
            return (Criteria) this;
        }

        public Criteria andCodLenguaNotBetween(String value1, String value2) {
            addCriterion("CODLENGUA_OFICIAL not between", value1, value2, "codLengua");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLikeInsensitive(String value) {
            addCriterion("upper(UID_INSTANCIA) like", value.toUpperCase(), "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andCodPaisLikeInsensitive(String value) {
            addCriterion("upper(CODPAIS) like", value.toUpperCase(), "codPais");
            return (Criteria) this;
        }

        public Criteria andDesPaisLikeInsensitive(String value) {
            addCriterion("upper(DESPAIS) like", value.toUpperCase(), "desPais");
            return (Criteria) this;
        }

        public Criteria andCodDivisaLikeInsensitive(String value) {
            addCriterion("upper(CODDIVISA) like", value.toUpperCase(), "codDivisa");
            return (Criteria) this;
        }

        public Criteria andCodLenguaLikeInsensitive(String value) {
            addCriterion("upper(CODLENGUA_OFICIAL) like", value.toUpperCase(), "codLengua");
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