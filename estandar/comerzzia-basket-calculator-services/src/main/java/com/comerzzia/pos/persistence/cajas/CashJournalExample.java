package com.comerzzia.pos.persistence.cajas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashJournalExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_UID_DIARIO_CAJA = "UID_DIARIO_CAJA";

    public static final String ORDER_BY_UID_DIARIO_CAJA_DESC = "UID_DIARIO_CAJA DESC";

    public static final String ORDER_BY_CODALM = "CODALM";

    public static final String ORDER_BY_CODALM_DESC = "CODALM DESC";

    public static final String ORDER_BY_CODCAJA = "CODCAJA";

    public static final String ORDER_BY_CODCAJA_DESC = "CODCAJA DESC";

    public static final String ORDER_BY_FECHA_APERTURA = "FECHA_APERTURA";

    public static final String ORDER_BY_FECHA_APERTURA_DESC = "FECHA_APERTURA DESC";

    public static final String ORDER_BY_FECHA_CIERRE = "FECHA_CIERRE";

    public static final String ORDER_BY_FECHA_CIERRE_DESC = "FECHA_CIERRE DESC";

    public static final String ORDER_BY_FECHA_ENVIO = "FECHA_ENVIO";

    public static final String ORDER_BY_FECHA_ENVIO_DESC = "FECHA_ENVIO DESC";

    public static final String ORDER_BY_USUARIO = "USUARIO";

    public static final String ORDER_BY_USUARIO_DESC = "USUARIO DESC";

    public static final String ORDER_BY_USUARIO_CIERRE = "USUARIO_CIERRE";

    public static final String ORDER_BY_USUARIO_CIERRE_DESC = "USUARIO_CIERRE DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CashJournalExample() {
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

        public Criteria andUidDiarioCajaIsNull() {
            addCriterion("UID_DIARIO_CAJA is null");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaIsNotNull() {
            addCriterion("UID_DIARIO_CAJA is not null");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaEqualTo(String value) {
            addCriterion("UID_DIARIO_CAJA =", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaNotEqualTo(String value) {
            addCriterion("UID_DIARIO_CAJA <>", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaGreaterThan(String value) {
            addCriterion("UID_DIARIO_CAJA >", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaGreaterThanOrEqualTo(String value) {
            addCriterion("UID_DIARIO_CAJA >=", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaLessThan(String value) {
            addCriterion("UID_DIARIO_CAJA <", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaLessThanOrEqualTo(String value) {
            addCriterion("UID_DIARIO_CAJA <=", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaLike(String value) {
            addCriterion("UID_DIARIO_CAJA like", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaNotLike(String value) {
            addCriterion("UID_DIARIO_CAJA not like", value, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaIn(List<String> values) {
            addCriterion("UID_DIARIO_CAJA in", values, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaNotIn(List<String> values) {
            addCriterion("UID_DIARIO_CAJA not in", values, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaBetween(String value1, String value2) {
            addCriterion("UID_DIARIO_CAJA between", value1, value2, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaNotBetween(String value1, String value2) {
            addCriterion("UID_DIARIO_CAJA not between", value1, value2, "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIsNull() {
            addCriterion("CODALM is null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIsNotNull() {
            addCriterion("CODALM is not null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenEqualTo(String value) {
            addCriterion("CODALM =", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotEqualTo(String value) {
            addCriterion("CODALM <>", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThan(String value) {
            addCriterion("CODALM >", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM >=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThan(String value) {
            addCriterion("CODALM <", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThanOrEqualTo(String value) {
            addCriterion("CODALM <=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLike(String value) {
            addCriterion("CODALM like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotLike(String value) {
            addCriterion("CODALM not like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIn(List<String> values) {
            addCriterion("CODALM in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotIn(List<String> values) {
            addCriterion("CODALM not in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenBetween(String value1, String value2) {
            addCriterion("CODALM between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotBetween(String value1, String value2) {
            addCriterion("CODALM not between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodcajaIsNull() {
            addCriterion("CODCAJA is null");
            return (Criteria) this;
        }

        public Criteria andCodcajaIsNotNull() {
            addCriterion("CODCAJA is not null");
            return (Criteria) this;
        }

        public Criteria andCodcajaEqualTo(String value) {
            addCriterion("CODCAJA =", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotEqualTo(String value) {
            addCriterion("CODCAJA <>", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaGreaterThan(String value) {
            addCriterion("CODCAJA >", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaGreaterThanOrEqualTo(String value) {
            addCriterion("CODCAJA >=", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLessThan(String value) {
            addCriterion("CODCAJA <", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLessThanOrEqualTo(String value) {
            addCriterion("CODCAJA <=", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLike(String value) {
            addCriterion("CODCAJA like", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotLike(String value) {
            addCriterion("CODCAJA not like", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaIn(List<String> values) {
            addCriterion("CODCAJA in", values, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotIn(List<String> values) {
            addCriterion("CODCAJA not in", values, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaBetween(String value1, String value2) {
            addCriterion("CODCAJA between", value1, value2, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotBetween(String value1, String value2) {
            addCriterion("CODCAJA not between", value1, value2, "codcaja");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaIsNull() {
            addCriterion("FECHA_APERTURA is null");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaIsNotNull() {
            addCriterion("FECHA_APERTURA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaEqualTo(Date value) {
            addCriterion("FECHA_APERTURA =", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaNotEqualTo(Date value) {
            addCriterion("FECHA_APERTURA <>", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaGreaterThan(Date value) {
            addCriterion("FECHA_APERTURA >", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_APERTURA >=", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaLessThan(Date value) {
            addCriterion("FECHA_APERTURA <", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_APERTURA <=", value, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaIn(List<Date> values) {
            addCriterion("FECHA_APERTURA in", values, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaNotIn(List<Date> values) {
            addCriterion("FECHA_APERTURA not in", values, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaBetween(Date value1, Date value2) {
            addCriterion("FECHA_APERTURA between", value1, value2, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaAperturaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_APERTURA not between", value1, value2, "fechaApertura");
            return (Criteria) this;
        }

        public Criteria andFechaCierreIsNull() {
            addCriterion("FECHA_CIERRE is null");
            return (Criteria) this;
        }

        public Criteria andFechaCierreIsNotNull() {
            addCriterion("FECHA_CIERRE is not null");
            return (Criteria) this;
        }

        public Criteria andFechaCierreEqualTo(Date value) {
            addCriterion("FECHA_CIERRE =", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreNotEqualTo(Date value) {
            addCriterion("FECHA_CIERRE <>", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreGreaterThan(Date value) {
            addCriterion("FECHA_CIERRE >", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_CIERRE >=", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreLessThan(Date value) {
            addCriterion("FECHA_CIERRE <", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_CIERRE <=", value, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreIn(List<Date> values) {
            addCriterion("FECHA_CIERRE in", values, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreNotIn(List<Date> values) {
            addCriterion("FECHA_CIERRE not in", values, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreBetween(Date value1, Date value2) {
            addCriterion("FECHA_CIERRE between", value1, value2, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaCierreNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_CIERRE not between", value1, value2, "fechaCierre");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioIsNull() {
            addCriterion("FECHA_ENVIO is null");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioIsNotNull() {
            addCriterion("FECHA_ENVIO is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioEqualTo(Date value) {
            addCriterion("FECHA_ENVIO =", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotEqualTo(Date value) {
            addCriterion("FECHA_ENVIO <>", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioGreaterThan(Date value) {
            addCriterion("FECHA_ENVIO >", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_ENVIO >=", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioLessThan(Date value) {
            addCriterion("FECHA_ENVIO <", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_ENVIO <=", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioIn(List<Date> values) {
            addCriterion("FECHA_ENVIO in", values, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotIn(List<Date> values) {
            addCriterion("FECHA_ENVIO not in", values, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioBetween(Date value1, Date value2) {
            addCriterion("FECHA_ENVIO between", value1, value2, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_ENVIO not between", value1, value2, "fechaEnvio");
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

        public Criteria andUsuarioCierreIsNull() {
            addCriterion("USUARIO_CIERRE is null");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreIsNotNull() {
            addCriterion("USUARIO_CIERRE is not null");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreEqualTo(String value) {
            addCriterion("USUARIO_CIERRE =", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreNotEqualTo(String value) {
            addCriterion("USUARIO_CIERRE <>", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreGreaterThan(String value) {
            addCriterion("USUARIO_CIERRE >", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreGreaterThanOrEqualTo(String value) {
            addCriterion("USUARIO_CIERRE >=", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreLessThan(String value) {
            addCriterion("USUARIO_CIERRE <", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreLessThanOrEqualTo(String value) {
            addCriterion("USUARIO_CIERRE <=", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreLike(String value) {
            addCriterion("USUARIO_CIERRE like", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreNotLike(String value) {
            addCriterion("USUARIO_CIERRE not like", value, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreIn(List<String> values) {
            addCriterion("USUARIO_CIERRE in", values, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreNotIn(List<String> values) {
            addCriterion("USUARIO_CIERRE not in", values, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreBetween(String value1, String value2) {
            addCriterion("USUARIO_CIERRE between", value1, value2, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreNotBetween(String value1, String value2) {
            addCriterion("USUARIO_CIERRE not between", value1, value2, "usuarioCierre");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaLikeInsensitive(String value) {
            addCriterion("upper(UID_DIARIO_CAJA) like", value.toUpperCase(), "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLikeInsensitive(String value) {
            addCriterion("upper(CODALM) like", value.toUpperCase(), "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodcajaLikeInsensitive(String value) {
            addCriterion("upper(CODCAJA) like", value.toUpperCase(), "codcaja");
            return (Criteria) this;
        }

        public Criteria andUsuarioLikeInsensitive(String value) {
            addCriterion("upper(USUARIO) like", value.toUpperCase(), "usuario");
            return (Criteria) this;
        }

        public Criteria andUsuarioCierreLikeInsensitive(String value) {
            addCriterion("upper(USUARIO_CIERRE) like", value.toUpperCase(), "usuarioCierre");
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