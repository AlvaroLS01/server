package com.comerzzia.pos.persistence.cajas.movimientos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashJournalLineExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_UID_DIARIO_CAJA = "UID_DIARIO_CAJA";

    public static final String ORDER_BY_UID_DIARIO_CAJA_DESC = "UID_DIARIO_CAJA DESC";

    public static final String ORDER_BY_LINEA = "LINEA";

    public static final String ORDER_BY_LINEA_DESC = "LINEA DESC";

    public static final String ORDER_BY_FECHA = "FECHA";

    public static final String ORDER_BY_FECHA_DESC = "FECHA DESC";

    public static final String ORDER_BY_CARGO = "CARGO";

    public static final String ORDER_BY_CARGO_DESC = "CARGO DESC";

    public static final String ORDER_BY_ABONO = "ABONO";

    public static final String ORDER_BY_ABONO_DESC = "ABONO DESC";

    public static final String ORDER_BY_CONCEPTO = "CONCEPTO";

    public static final String ORDER_BY_CONCEPTO_DESC = "CONCEPTO DESC";

    public static final String ORDER_BY_DOCUMENTO = "DOCUMENTO";

    public static final String ORDER_BY_DOCUMENTO_DESC = "DOCUMENTO DESC";

    public static final String ORDER_BY_CODMEDPAG = "CODMEDPAG";

    public static final String ORDER_BY_CODMEDPAG_DESC = "CODMEDPAG DESC";

    public static final String ORDER_BY_ID_DOCUMENTO = "ID_DOCUMENTO";

    public static final String ORDER_BY_ID_DOCUMENTO_DESC = "ID_DOCUMENTO DESC";

    public static final String ORDER_BY_CODCONCEPTO_MOV = "CODCONCEPTO_MOV";

    public static final String ORDER_BY_CODCONCEPTO_MOV_DESC = "CODCONCEPTO_MOV DESC";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO = "ID_TIPO_DOCUMENTO";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO_DESC = "ID_TIPO_DOCUMENTO DESC";

    public static final String ORDER_BY_UID_TRANSACCION_DET = "UID_TRANSACCION_DET";

    public static final String ORDER_BY_UID_TRANSACCION_DET_DESC = "UID_TRANSACCION_DET DESC";

    public static final String ORDER_BY_CODDIVISA = "CODDIVISA";

    public static final String ORDER_BY_CODDIVISA_DESC = "CODDIVISA DESC";

    public static final String ORDER_BY_TIPO_DE_CAMBIO = "TIPO_DE_CAMBIO";

    public static final String ORDER_BY_TIPO_DE_CAMBIO_DESC = "TIPO_DE_CAMBIO DESC";

    public static final String ORDER_BY_USUARIO = "USUARIO";

    public static final String ORDER_BY_USUARIO_DESC = "USUARIO DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CashJournalLineExample() {
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

        public Criteria andLineaIsNull() {
            addCriterion("LINEA is null");
            return (Criteria) this;
        }

        public Criteria andLineaIsNotNull() {
            addCriterion("LINEA is not null");
            return (Criteria) this;
        }

        public Criteria andLineaEqualTo(Integer value) {
            addCriterion("LINEA =", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaNotEqualTo(Integer value) {
            addCriterion("LINEA <>", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaGreaterThan(Integer value) {
            addCriterion("LINEA >", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINEA >=", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaLessThan(Integer value) {
            addCriterion("LINEA <", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaLessThanOrEqualTo(Integer value) {
            addCriterion("LINEA <=", value, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaIn(List<Integer> values) {
            addCriterion("LINEA in", values, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaNotIn(List<Integer> values) {
            addCriterion("LINEA not in", values, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaBetween(Integer value1, Integer value2) {
            addCriterion("LINEA between", value1, value2, "linea");
            return (Criteria) this;
        }

        public Criteria andLineaNotBetween(Integer value1, Integer value2) {
            addCriterion("LINEA not between", value1, value2, "linea");
            return (Criteria) this;
        }

        public Criteria andFechaIsNull() {
            addCriterion("FECHA is null");
            return (Criteria) this;
        }

        public Criteria andFechaIsNotNull() {
            addCriterion("FECHA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEqualTo(Date value) {
            addCriterion("FECHA =", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotEqualTo(Date value) {
            addCriterion("FECHA <>", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThan(Date value) {
            addCriterion("FECHA >", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA >=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThan(Date value) {
            addCriterion("FECHA <", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA <=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaIn(List<Date> values) {
            addCriterion("FECHA in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotIn(List<Date> values) {
            addCriterion("FECHA not in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaBetween(Date value1, Date value2) {
            addCriterion("FECHA between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA not between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andCargoIsNull() {
            addCriterion("CARGO is null");
            return (Criteria) this;
        }

        public Criteria andCargoIsNotNull() {
            addCriterion("CARGO is not null");
            return (Criteria) this;
        }

        public Criteria andCargoEqualTo(BigDecimal value) {
            addCriterion("CARGO =", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoNotEqualTo(BigDecimal value) {
            addCriterion("CARGO <>", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoGreaterThan(BigDecimal value) {
            addCriterion("CARGO >", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CARGO >=", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoLessThan(BigDecimal value) {
            addCriterion("CARGO <", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CARGO <=", value, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoIn(List<BigDecimal> values) {
            addCriterion("CARGO in", values, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoNotIn(List<BigDecimal> values) {
            addCriterion("CARGO not in", values, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARGO between", value1, value2, "cargo");
            return (Criteria) this;
        }

        public Criteria andCargoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARGO not between", value1, value2, "cargo");
            return (Criteria) this;
        }

        public Criteria andAbonoIsNull() {
            addCriterion("ABONO is null");
            return (Criteria) this;
        }

        public Criteria andAbonoIsNotNull() {
            addCriterion("ABONO is not null");
            return (Criteria) this;
        }

        public Criteria andAbonoEqualTo(BigDecimal value) {
            addCriterion("ABONO =", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoNotEqualTo(BigDecimal value) {
            addCriterion("ABONO <>", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoGreaterThan(BigDecimal value) {
            addCriterion("ABONO >", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ABONO >=", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoLessThan(BigDecimal value) {
            addCriterion("ABONO <", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ABONO <=", value, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoIn(List<BigDecimal> values) {
            addCriterion("ABONO in", values, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoNotIn(List<BigDecimal> values) {
            addCriterion("ABONO not in", values, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ABONO between", value1, value2, "abono");
            return (Criteria) this;
        }

        public Criteria andAbonoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ABONO not between", value1, value2, "abono");
            return (Criteria) this;
        }

        public Criteria andConceptoIsNull() {
            addCriterion("CONCEPTO is null");
            return (Criteria) this;
        }

        public Criteria andConceptoIsNotNull() {
            addCriterion("CONCEPTO is not null");
            return (Criteria) this;
        }

        public Criteria andConceptoEqualTo(String value) {
            addCriterion("CONCEPTO =", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoNotEqualTo(String value) {
            addCriterion("CONCEPTO <>", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoGreaterThan(String value) {
            addCriterion("CONCEPTO >", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoGreaterThanOrEqualTo(String value) {
            addCriterion("CONCEPTO >=", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoLessThan(String value) {
            addCriterion("CONCEPTO <", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoLessThanOrEqualTo(String value) {
            addCriterion("CONCEPTO <=", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoLike(String value) {
            addCriterion("CONCEPTO like", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoNotLike(String value) {
            addCriterion("CONCEPTO not like", value, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoIn(List<String> values) {
            addCriterion("CONCEPTO in", values, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoNotIn(List<String> values) {
            addCriterion("CONCEPTO not in", values, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoBetween(String value1, String value2) {
            addCriterion("CONCEPTO between", value1, value2, "concepto");
            return (Criteria) this;
        }

        public Criteria andConceptoNotBetween(String value1, String value2) {
            addCriterion("CONCEPTO not between", value1, value2, "concepto");
            return (Criteria) this;
        }

        public Criteria andDocumentoIsNull() {
            addCriterion("DOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andDocumentoIsNotNull() {
            addCriterion("DOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andDocumentoEqualTo(String value) {
            addCriterion("DOCUMENTO =", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoNotEqualTo(String value) {
            addCriterion("DOCUMENTO <>", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoGreaterThan(String value) {
            addCriterion("DOCUMENTO >", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoGreaterThanOrEqualTo(String value) {
            addCriterion("DOCUMENTO >=", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoLessThan(String value) {
            addCriterion("DOCUMENTO <", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoLessThanOrEqualTo(String value) {
            addCriterion("DOCUMENTO <=", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoLike(String value) {
            addCriterion("DOCUMENTO like", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoNotLike(String value) {
            addCriterion("DOCUMENTO not like", value, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoIn(List<String> values) {
            addCriterion("DOCUMENTO in", values, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoNotIn(List<String> values) {
            addCriterion("DOCUMENTO not in", values, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoBetween(String value1, String value2) {
            addCriterion("DOCUMENTO between", value1, value2, "documento");
            return (Criteria) this;
        }

        public Criteria andDocumentoNotBetween(String value1, String value2) {
            addCriterion("DOCUMENTO not between", value1, value2, "documento");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoIsNull() {
            addCriterion("CODMEDPAG is null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoIsNotNull() {
            addCriterion("CODMEDPAG is not null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoEqualTo(String value) {
            addCriterion("CODMEDPAG =", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotEqualTo(String value) {
            addCriterion("CODMEDPAG <>", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoGreaterThan(String value) {
            addCriterion("CODMEDPAG >", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG >=", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLessThan(String value) {
            addCriterion("CODMEDPAG <", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG <=", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLike(String value) {
            addCriterion("CODMEDPAG like", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotLike(String value) {
            addCriterion("CODMEDPAG not like", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoIn(List<String> values) {
            addCriterion("CODMEDPAG in", values, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotIn(List<String> values) {
            addCriterion("CODMEDPAG not in", values, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoBetween(String value1, String value2) {
            addCriterion("CODMEDPAG between", value1, value2, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG not between", value1, value2, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoIsNull() {
            addCriterion("ID_DOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoIsNotNull() {
            addCriterion("ID_DOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoEqualTo(String value) {
            addCriterion("ID_DOCUMENTO =", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoNotEqualTo(String value) {
            addCriterion("ID_DOCUMENTO <>", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoGreaterThan(String value) {
            addCriterion("ID_DOCUMENTO >", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoGreaterThanOrEqualTo(String value) {
            addCriterion("ID_DOCUMENTO >=", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoLessThan(String value) {
            addCriterion("ID_DOCUMENTO <", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoLessThanOrEqualTo(String value) {
            addCriterion("ID_DOCUMENTO <=", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoLike(String value) {
            addCriterion("ID_DOCUMENTO like", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoNotLike(String value) {
            addCriterion("ID_DOCUMENTO not like", value, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoIn(List<String> values) {
            addCriterion("ID_DOCUMENTO in", values, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoNotIn(List<String> values) {
            addCriterion("ID_DOCUMENTO not in", values, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoBetween(String value1, String value2) {
            addCriterion("ID_DOCUMENTO between", value1, value2, "idDocumento");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoNotBetween(String value1, String value2) {
            addCriterion("ID_DOCUMENTO not between", value1, value2, "idDocumento");
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

        public Criteria andUidTransaccionDetIsNull() {
            addCriterion("UID_TRANSACCION_DET is null");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetIsNotNull() {
            addCriterion("UID_TRANSACCION_DET is not null");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetEqualTo(String value) {
            addCriterion("UID_TRANSACCION_DET =", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetNotEqualTo(String value) {
            addCriterion("UID_TRANSACCION_DET <>", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetGreaterThan(String value) {
            addCriterion("UID_TRANSACCION_DET >", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetGreaterThanOrEqualTo(String value) {
            addCriterion("UID_TRANSACCION_DET >=", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetLessThan(String value) {
            addCriterion("UID_TRANSACCION_DET <", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetLessThanOrEqualTo(String value) {
            addCriterion("UID_TRANSACCION_DET <=", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetLike(String value) {
            addCriterion("UID_TRANSACCION_DET like", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetNotLike(String value) {
            addCriterion("UID_TRANSACCION_DET not like", value, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetIn(List<String> values) {
            addCriterion("UID_TRANSACCION_DET in", values, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetNotIn(List<String> values) {
            addCriterion("UID_TRANSACCION_DET not in", values, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetBetween(String value1, String value2) {
            addCriterion("UID_TRANSACCION_DET between", value1, value2, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetNotBetween(String value1, String value2) {
            addCriterion("UID_TRANSACCION_DET not between", value1, value2, "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andCoddivisaIsNull() {
            addCriterion("CODDIVISA is null");
            return (Criteria) this;
        }

        public Criteria andCoddivisaIsNotNull() {
            addCriterion("CODDIVISA is not null");
            return (Criteria) this;
        }

        public Criteria andCoddivisaEqualTo(String value) {
            addCriterion("CODDIVISA =", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaNotEqualTo(String value) {
            addCriterion("CODDIVISA <>", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaGreaterThan(String value) {
            addCriterion("CODDIVISA >", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaGreaterThanOrEqualTo(String value) {
            addCriterion("CODDIVISA >=", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaLessThan(String value) {
            addCriterion("CODDIVISA <", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaLessThanOrEqualTo(String value) {
            addCriterion("CODDIVISA <=", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaLike(String value) {
            addCriterion("CODDIVISA like", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaNotLike(String value) {
            addCriterion("CODDIVISA not like", value, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaIn(List<String> values) {
            addCriterion("CODDIVISA in", values, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaNotIn(List<String> values) {
            addCriterion("CODDIVISA not in", values, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaBetween(String value1, String value2) {
            addCriterion("CODDIVISA between", value1, value2, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andCoddivisaNotBetween(String value1, String value2) {
            addCriterion("CODDIVISA not between", value1, value2, "coddivisa");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioIsNull() {
            addCriterion("TIPO_DE_CAMBIO is null");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioIsNotNull() {
            addCriterion("TIPO_DE_CAMBIO is not null");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioEqualTo(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO =", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioNotEqualTo(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO <>", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioGreaterThan(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO >", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO >=", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioLessThan(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO <", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TIPO_DE_CAMBIO <=", value, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioIn(List<BigDecimal> values) {
            addCriterion("TIPO_DE_CAMBIO in", values, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioNotIn(List<BigDecimal> values) {
            addCriterion("TIPO_DE_CAMBIO not in", values, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TIPO_DE_CAMBIO between", value1, value2, "tipoDeCambio");
            return (Criteria) this;
        }

        public Criteria andTipoDeCambioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TIPO_DE_CAMBIO not between", value1, value2, "tipoDeCambio");
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

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidDiarioCajaLikeInsensitive(String value) {
            addCriterion("upper(UID_DIARIO_CAJA) like", value.toUpperCase(), "uidDiarioCaja");
            return (Criteria) this;
        }

        public Criteria andConceptoLikeInsensitive(String value) {
            addCriterion("upper(CONCEPTO) like", value.toUpperCase(), "concepto");
            return (Criteria) this;
        }

        public Criteria andDocumentoLikeInsensitive(String value) {
            addCriterion("upper(DOCUMENTO) like", value.toUpperCase(), "documento");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG) like", value.toUpperCase(), "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andIdDocumentoLikeInsensitive(String value) {
            addCriterion("upper(ID_DOCUMENTO) like", value.toUpperCase(), "idDocumento");
            return (Criteria) this;
        }

        public Criteria andCodConceptoMovimientoLikeInsensitive(String value) {
            addCriterion("upper(CODCONCEPTO_MOV) like", value.toUpperCase(), "codConceptoMovimiento");
            return (Criteria) this;
        }

        public Criteria andUidTransaccionDetLikeInsensitive(String value) {
            addCriterion("upper(UID_TRANSACCION_DET) like", value.toUpperCase(), "uidTransaccionDet");
            return (Criteria) this;
        }

        public Criteria andCoddivisaLikeInsensitive(String value) {
            addCriterion("upper(CODDIVISA) like", value.toUpperCase(), "coddivisa");
            return (Criteria) this;
        }

        public Criteria andUsuarioLikeInsensitive(String value) {
            addCriterion("upper(USUARIO) like", value.toUpperCase(), "usuario");
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