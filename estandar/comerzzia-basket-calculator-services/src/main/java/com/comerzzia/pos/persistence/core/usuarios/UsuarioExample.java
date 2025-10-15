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
package com.comerzzia.pos.persistence.core.usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuarioExample {
    public static final String ORDER_BY_UID_INSTANCIA = "UID_INSTANCIA";

    public static final String ORDER_BY_UID_INSTANCIA_DESC = "UID_INSTANCIA DESC";

    public static final String ORDER_BY_ID_USUARIO = "ID_USUARIO";

    public static final String ORDER_BY_ID_USUARIO_DESC = "ID_USUARIO DESC";

    public static final String ORDER_BY_USUARIO = "USUARIO";

    public static final String ORDER_BY_USUARIO_DESC = "USUARIO DESC";

    public static final String ORDER_BY_DESUSUARIO = "DESUSUARIO";

    public static final String ORDER_BY_DESUSUARIO_DESC = "DESUSUARIO DESC";

    public static final String ORDER_BY_CLAVE = "CLAVE";

    public static final String ORDER_BY_CLAVE_DESC = "CLAVE DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";
    
    public static final String ORDER_BY_UID_MENU_POR_DEFECTO = "UID_MENU_POR_DEFECTO";

    public static final String ORDER_BY_PUEDE_CAMBIAR_MENU = "PUEDE_CAMBIAR_MENU";

    public static final String ORDER_BY_PUEDE_CAMBIAR_MENU_DESC = "PUEDE_CAMBIAR_MENU DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UsuarioExample() {
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

        public Criteria andUsuarioIsNull() {
            addCriterion("USUARIO is null");
            return (Criteria) this;
        }

        public Criteria andUsuarioIsNotNull() {
            addCriterion("USUARIO is not null");
            return (Criteria) this;
        }

        public Criteria andUsuarioEqualTo(String value) {
            addCriterion("USUARIO =", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioNotEqualTo(String value) {
            addCriterion("USUARIO <>", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioGreaterThan(String value) {
            addCriterion("USUARIO >", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioGreaterThanOrEqualTo(String value) {
            addCriterion("USUARIO >=", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioLessThan(String value) {
            addCriterion("USUARIO <", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioLessThanOrEqualTo(String value) {
            addCriterion("USUARIO <=", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioLike(String value) {
            addCriterion("USUARIO like", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioNotLike(String value) {
            addCriterion("USUARIO not like", value, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioIn(List<String> values) {
            addCriterion("USUARIO in", values, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioNotIn(List<String> values) {
            addCriterion("USUARIO not in", values, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioBetween(String value1, String value2) {
            addCriterion("USUARIO between", value1, value2, "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioNotBetween(String value1, String value2) {
            addCriterion("USUARIO not between", value1, value2, "usuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioIsNull() {
            addCriterion("DESUSUARIO is null");
            return (Criteria) this;
        }

        public Criteria andDesusuarioIsNotNull() {
            addCriterion("DESUSUARIO is not null");
            return (Criteria) this;
        }

        public Criteria andDesusuarioEqualTo(String value) {
            addCriterion("DESUSUARIO =", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioNotEqualTo(String value) {
            addCriterion("DESUSUARIO <>", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioGreaterThan(String value) {
            addCriterion("DESUSUARIO >", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioGreaterThanOrEqualTo(String value) {
            addCriterion("DESUSUARIO >=", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioLessThan(String value) {
            addCriterion("DESUSUARIO <", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioLessThanOrEqualTo(String value) {
            addCriterion("DESUSUARIO <=", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioLike(String value) {
            addCriterion("DESUSUARIO like", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioNotLike(String value) {
            addCriterion("DESUSUARIO not like", value, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioIn(List<String> values) {
            addCriterion("DESUSUARIO in", values, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioNotIn(List<String> values) {
            addCriterion("DESUSUARIO not in", values, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioBetween(String value1, String value2) {
            addCriterion("DESUSUARIO between", value1, value2, "desusuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioNotBetween(String value1, String value2) {
            addCriterion("DESUSUARIO not between", value1, value2, "desusuario");
            return (Criteria) this;
        }

        public Criteria andClaveIsNull() {
            addCriterion("CLAVE is null");
            return (Criteria) this;
        }

        public Criteria andClaveIsNotNull() {
            addCriterion("CLAVE is not null");
            return (Criteria) this;
        }

        public Criteria andClaveEqualTo(String value) {
            addCriterion("CLAVE =", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveNotEqualTo(String value) {
            addCriterion("CLAVE <>", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveGreaterThan(String value) {
            addCriterion("CLAVE >", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveGreaterThanOrEqualTo(String value) {
            addCriterion("CLAVE >=", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveLessThan(String value) {
            addCriterion("CLAVE <", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveLessThanOrEqualTo(String value) {
            addCriterion("CLAVE <=", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveLike(String value) {
            addCriterion("CLAVE like", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveNotLike(String value) {
            addCriterion("CLAVE not like", value, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveIn(List<String> values) {
            addCriterion("CLAVE in", values, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveNotIn(List<String> values) {
            addCriterion("CLAVE not in", values, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveBetween(String value1, String value2) {
            addCriterion("CLAVE between", value1, value2, "clave");
            return (Criteria) this;
        }

        public Criteria andClaveNotBetween(String value1, String value2) {
            addCriterion("CLAVE not between", value1, value2, "clave");
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

        public Criteria andMenuPorDefectoIsNull() {
            addCriterion("UID_MENU_POR_DEFECTO is null");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoIsNotNull() {
            addCriterion("UID_MENU_POR_DEFECTO is not null");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoEqualTo(String value) {
            addCriterion("UID_MENU_POR_DEFECTO =", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoNotEqualTo(String value) {
            addCriterion("UID_MENU_POR_DEFECTO <>", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoGreaterThan(String value) {
            addCriterion("UID_MENU_POR_DEFECTO >", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoGreaterThanOrEqualTo(String value) {
            addCriterion("UID_MENU_POR_DEFECTO >=", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoLessThan(String value) {
            addCriterion("UID_MENU_POR_DEFECTO <", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoLessThanOrEqualTo(String value) {
            addCriterion("UID_MENU_POR_DEFECTO <=", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoLike(String value) {
            addCriterion("UID_MENU_POR_DEFECTO like", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoNotLike(String value) {
            addCriterion("UID_MENU_POR_DEFECTO not like", value, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoIn(List<String> values) {
            addCriterion("UID_MENU_POR_DEFECTO in", values, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoNotIn(List<String> values) {
            addCriterion("UID_MENU_POR_DEFECTO not in", values, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoBetween(String value1, String value2) {
            addCriterion("UID_MENU_POR_DEFECTO between", value1, value2, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoNotBetween(String value1, String value2) {
            addCriterion("UID_MENU_POR_DEFECTO not between", value1, value2, "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionIsNull() {
            addCriterion("PUEDE_CAMBIAR_MENU is null");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionIsNotNull() {
            addCriterion("PUEDE_CAMBIAR_MENU is not null");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionEqualTo(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU =", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionNotEqualTo(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU <>", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionGreaterThan(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU >", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionGreaterThanOrEqualTo(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU >=", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionLessThan(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU <", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionLessThanOrEqualTo(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU <=", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionLike(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU like", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionNotLike(String value) {
            addCriterion("PUEDE_CAMBIAR_MENU not like", value, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionIn(List<String> values) {
            addCriterion("PUEDE_CAMBIAR_MENU in", values, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionNotIn(List<String> values) {
            addCriterion("PUEDE_CAMBIAR_MENU not in", values, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionBetween(String value1, String value2) {
            addCriterion("PUEDE_CAMBIAR_MENU between", value1, value2, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionNotBetween(String value1, String value2) {
            addCriterion("PUEDE_CAMBIAR_MENU not between", value1, value2, "puedeCambiarMenu");
            return (Criteria) this;
        }

        public Criteria andUidInstanciaLikeInsensitive(String value) {
            addCriterion("upper(UID_INSTANCIA) like", value.toUpperCase(), "uidInstancia");
            return (Criteria) this;
        }

        public Criteria andUsuarioLikeInsensitive(String value) {
            addCriterion("upper(USUARIO) like", value.toUpperCase(), "usuario");
            return (Criteria) this;
        }

        public Criteria andDesusuarioLikeInsensitive(String value) {
            addCriterion("upper(DESUSUARIO) like", value.toUpperCase(), "desusuario");
            return (Criteria) this;
        }

        public Criteria andClaveLikeInsensitive(String value) {
            addCriterion("upper(CLAVE) like", value.toUpperCase(), "clave");
            return (Criteria) this;
        }

        public Criteria andAplicacionPorDefectoLikeInsensitive(String value) {
            addCriterion("upper(UID_MENU_POR_DEFECTO) like", value.toUpperCase(), "menuPorDefecto");
            return (Criteria) this;
        }

        public Criteria andPuedeCambiarAplicacionLikeInsensitive(String value) {
            addCriterion("upper(PUEDE_CAMBIAR_MENU) like", value.toUpperCase(), "puedeCambiarMenu");
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