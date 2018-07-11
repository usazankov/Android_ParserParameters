package com.inpas.parserparameters;

import java.util.HashMap;
import java.util.Map;

public class TagsTLV {
    static Map<Integer, String> tlvFields = new HashMap<Integer, String>();
    static Map<Integer, String> tlvFieldsRoot = new HashMap<Integer, String>();
    static {
        init();
    }
    public static void init() {

        tlvFieldsRoot.put(32840,"CurrencyPreset");
        tlvFieldsRoot.put(32792,"PaymentSystemPreset");
        tlvFieldsRoot.put(32788,"CardProductPreset");
        tlvFieldsRoot.put(32832,"SecurityKeyPreset");
        tlvFieldsRoot.put(32830,"AccountTypePreset");
        tlvFieldsRoot.put(32843, "TemplatePreset");
        tlvFieldsRoot.put(32808, "TerminalProfilePreset");
        tlvFieldsRoot.put(32897, "UsersGroupPreset");
        tlvFieldsRoot.put(32807, "PossessorPreset");
        tlvFieldsRoot.put(32846, "AcquiringPreset");
        tlvFieldsRoot.put(32965, "ConnectionsServerPreset");
        tlvFieldsRoot.put(32769, "Terminal");

        tlvFields.put(0,"Anchor");

        //CurrencyPreset
        tlvFields.put(32797, "Currency");
        tlvFields.put(1027, "Name");
        tlvFields.put(1072, "NumericCode");
        tlvFields.put(1073, "AlphabeticCode");
        tlvFields.put(1074, "MinorUnit");
        tlvFields.put(1075, "ConversionFactor");
        tlvFields.put(32790, "BinRanges");
        tlvFields.put(32791, "DefBinRange");
        tlvFields.put(1055, "PanLengthStart");
        tlvFields.put(1056, "PanLengthFinish");
        tlvFields.put(1057, "FromBin");
        tlvFields.put(1058, "ToBin");

        //PaymentSystemPreset
        tlvFields.put(32793,"PaymentSystem");
        tlvFields.put(32794,"EmvCAPKs");
        tlvFields.put(1027,"Name");
        tlvFields.put(1166,"PName");
        tlvFields.put(1066,"HotListPath");
        tlvFields.put(1062,"RID");
        tlvFields.put(1103,"EmvTDOL");
        tlvFields.put(1104,"EmvDDOL");
        tlvFields.put(1154,"ReferralCallCenter");
        tlvFields.put(32795,"EmvCAPK");
        tlvFields.put(1067,"Index");
        tlvFields.put(1068,"Length");
        tlvFields.put(1070,"Exponent");
        tlvFields.put(1069,"Module");
        tlvFields.put(1098,"CheckValue");
        tlvFields.put(1071,"ExpiryDate");
        tlvFields.put(32950,"RevocationSertificates");
        tlvFields.put(32951,"RevocationSertificate");
        tlvFields.put(1494,"SertSerialNumber");

        //CardProductPreset
        tlvFields.put(32789,"CardProduct");
        tlvFields.put(1167,"CPName");
        tlvFields.put(1027,"Name");
        tlvFields.put(1054,"PIX");
        tlvFields.put(1062,"RID");
        tlvFields.put(32790,"BinRanges");
        tlvFields.put(1059,"ManualInput");
        tlvFields.put(1154,"ReferralCallCenter");

        //SecurityKeyPreset
        tlvFields.put(32882,"SecurityKeyTemplate");
        tlvFields.put(1283,"Pinpad");
        tlvFields.put(32883,"MKeys");
        tlvFields.put(32884,"MKey");
        tlvFields.put(1109,"KeyProfile");
        tlvFields.put(1110,"SlotNo");

        //AccountTypePreset
        tlvFields.put(32831,"AccountType");
        tlvFields.put(1107,"AccountTypeId");
        tlvFields.put(1114,"EnabledOperations");
        tlvFields.put(32849,"PrefLanguages");
        tlvFields.put(32850,"PrefLanguageItem");
        tlvFields.put(1125,"PrefLanguage");
        tlvFields.put(1126,"Item");

        //TemplatePreset
        tlvFields.put(32842, "Template");
        tlvFields.put(1128, "TemplateName");
        tlvFields.put(1121, "TemplateType");
        tlvFields.put(1122, "TemplateBody");

        //UserGroupPreset
        tlvFields.put(32896, "UsersGroup");
        tlvFields.put(1304, "UsersGroupName");
        tlvFields.put(1305, "UsersGroupEnabledFinOperations");
        tlvFields.put(1306, "UsersGroupEnabledServiceOperations");
        tlvFields.put(1311, "UsersGroupRole");

        //ConnectionsServerPreset

        //TerminalProfilePreset
        tlvFields.put(32809, "TerminalProfile");
        tlvFields.put(1027,"Name");
        tlvFields.put(1034, "TerminalType");
        tlvFields.put(32780, "CardProductRules");
        tlvFields.put(32781, "CardProductRule");
        tlvFields.put(1035, "CardProductRef");
        tlvFields.put(1169, "CardTechnology");
        tlvFields.put(1060, "EMVAppSelectIndicator");
        tlvFields.put(1114, "EnabledOperations");
        tlvFields.put(1170, "TransactionLimit");
        tlvFields.put(1601, "TransactionLimitForRefund");
        tlvFields.put(1435, "OnDeviceTransactionLimit");
        tlvFields.put(1191, "FloorLimitDomestic");
        tlvFields.put(1192, "FloorLimitInternational");
        tlvFields.put(32888, "MagCVMs");
        tlvFields.put(32889, "MagCVM");
        tlvFields.put(1267, "MagCVM_Operations");
        tlvFields.put(1258, "MagCVM_Metods");
        tlvFields.put(1418, "MagCVMAmount");
        tlvFields.put(32927, "ChipCVM");
        tlvFields.put(1419, "UseChipCVM");
        tlvFields.put(1420, "ChipCVMAmount");
        tlvFields.put(1049, "EmvTerminalCapabilities");
        tlvFields.put(1175, "EmvAdditionalTerminalCapabilities");
        tlvFields.put(1045, "CvmLimit");
        tlvFields.put(1193, "TransactionSchema");
        tlvFields.put(1602, "CtlssMCKernelConfig");
        tlvFields.put(1612, "CtlssMirTPMCaps");
        tlvFields.put(1613, "CtlssMirDataExchTagList");
        tlvFields.put(1194, "AdditionalData");
        tlvFields.put(1200, "CvmCapability_NoCvmRequired");
        tlvFields.put(1201, "MagStrCvmCapability_NoCvmRequired");
        tlvFields.put(1202, "MagStrCvmCapability_CvmRequired");
        tlvFields.put(1175, "EmvAdditionalTerminalCapabilities");
        tlvFields.put(1195, "CtlssVisaTransactionQualifiers");
        tlvFields.put(1521, "CtlssCupTransactionQualifiers");
        tlvFields.put(1532, "TerminalInterchangeProfile");
        tlvFields.put(1533, "CombinationOptions");
        tlvFields.put(1040, "TAC_Denial");
        tlvFields.put(1041, "TAC_Online");
        tlvFields.put(1042, "TAC_Default");
        tlvFields.put(1196, "CtlssMCForceMagstripe");
        tlvFields.put(1198, "CtlssPPassMagstripeVersion");
        tlvFields.put(1184, "SkipCheckExpDate");
        tlvFields.put(1411, "AllowFallback");
        tlvFields.put(1047, "VisualCheck");
        tlvFields.put(1048, "ForceOnline");
        tlvFields.put(1116, "MotoOperations");
        tlvFields.put(32851, "MidleReceiptRefs");
        tlvFields.put(1129, "TemplateRef");
        tlvFields.put(32848, "FinalReceiptRefs");
        tlvFields.put(32847, "ReportRefs");
        tlvFields.put(1050, "EmvAdditionalTerminalCapabilities");
        tlvFields.put(1052, "BypassPIN");
        tlvFields.put(1053, "UseDirectCancel");
        tlvFields.put(1061, "UseManualEnter");
        tlvFields.put(1379, "UseSoundNotAproved");
        tlvFields.put(1479, "UseSoundRemoveCard");
        tlvFields.put(1408, "AutoReversalAlert");
        tlvFields.put(1436, "MaxOperationsInBatch");
        tlvFields.put(1251, "HotlinePhoneMessage");
        tlvFields.put(1051, "UnableToGoOnlineCase");
        tlvFields.put(1095, "EmvThresholdValue");
        tlvFields.put(1096, "EmvTargetPercentage");
        tlvFields.put(1097, "EmvMaximumTargetPercentage");
        tlvFields.put(1287, "CardHolderConfirmAmount");

        //PossessorPreset
        tlvFields.put(32806, "Possessor");
        tlvFields.put(1030, "MCC");
        tlvFields.put(1031, "PossessorType");
        tlvFields.put(1130, "EmvMerchantNameLocation");
        tlvFields.put(32804, "PaymentSystemRegistrations");
        tlvFields.put(32802, "PaymentSystemRegistration");
        tlvFields.put(1032, "MerchantId");
        tlvFields.put(1013, "PaymentSystemRef");
        tlvFields.put(32840, "CurrencyRefs");
        tlvFields.put(1014, "CurrencyRef");
        tlvFields.put(1337, "GoodsProfileRefs");
        tlvFields.put(32901, "UsersPreset");
        tlvFields.put(32902, "UsersPresetRegistration");
        tlvFields.put(1307, "UserName");
        tlvFields.put(1308, "UserPassword");
        tlvFields.put(1309, "UserGroupRef");
        tlvFields.put(1033, "SelectLanguageDialog");
        tlvFields.put(1414, "InputAdditionalDataForSale");
        tlvFields.put(1415, "AdditionalDataDisplayName");
        tlvFields.put(1416, "AdditionalDataUsage");
        tlvFields.put(1351, "PicturesGroupTypeSelectedPossessor");
        tlvFields.put(1352, "PicturesGroupSelectedPossessor");
        tlvFields.put(1368, "PrintGroupTypeSelectedPossessor");
        tlvFields.put(1369, "PrintGroupSelectedPossessor");
        tlvFields.put(1371, "PrintPictureName");
        tlvFields.put(1372, "PrintPictureFileName");
        tlvFields.put(32853, "GluedReports");
        tlvFields.put(1270, "ReconciliationReport");
        tlvFields.put(1271, "ReconciliationReportFail");
        tlvFields.put(32847, "ReportRefs");
        tlvFields.put(1168, "Logo");
        tlvFields.put(1287, "CardHolderConfirmAmount");
        tlvFields.put(1287, "CardHolderConfirmAmount");

        //AcquiringPreset
        tlvFields.put(32841, "Acquiring");
        tlvFields.put(1131, "AcquiringName");
        tlvFields.put(32837, "PaymentSystemLinks");
        tlvFields.put(32836, "PaymentSystemLink");
        tlvFields.put(1117, "PaymentSystemRef");
        tlvFields.put(1118, "AcquirerIdentifier");
        tlvFields.put(32866, "AuthorisationHosts");
        tlvFields.put(1618, "Host");
        tlvFields.put(1146, "HostTimeZone");
        tlvFields.put(1155, "SummerTime");
        tlvFields.put(32852, "Reports");
        tlvFields.put(1077, "ReconciliationReport");
        tlvFields.put(1132, "KeyExchangeReport");
        tlvFields.put(1133, "CheckLinkReport");
        tlvFields.put(32863, "TechnicalParameters");
        tlvFields.put(1147, "ReconciliationType");
        tlvFields.put(1185, "EmptyReconciliation");
        tlvFields.put(1149, "KeySchema");
        tlvFields.put(1152, "SecureIso");
        tlvFields.put(1280, "BatchOverflowReconciliation");
        tlvFields.put(1281, "MaxOffline");
        tlvFields.put(1374, "UseIssuerRefferal");
        tlvFields.put(1542, "ResponseTimeout");

        //ConnectionsServerPreset
        tlvFields.put(32966, "ConnectionsServer");
        tlvFields.put(1618, "ConnectionName");
        tlvFields.put(1410, "SSLConfigServerPortCertificatePath");
        tlvFields.put(1080, "SSLClientCertificatePath");
        tlvFields.put(1164, "SSLPrivateKeyPath");
        tlvFields.put(1002, "Address");

        //TerminalPreset
        tlvFields.put(1165, "XSD_Version");
        tlvFields.put(1001, "TerminalId");
        tlvFields.put(1438, "OwnerName");
        tlvFields.put(1002, "Address");
        tlvFields.put(1143, "TerminalTimeZone");
        tlvFields.put(1155, "SummerTime");
        tlvFields.put(1003, "Model");
        tlvFields.put(1112, "TerminalCountryCode");
        tlvFields.put(1204, "PriorLicense");
        tlvFields.put(1440, "UseMainMenuPassword");
        tlvFields.put(1441, "MainMenuPassword");
        tlvFields.put(1250, "UploadLogsFlag");
        tlvFields.put(1404, "LogSize");
        tlvFields.put(1406, "LogMode");
        tlvFields.put(32885, "Logging");
        tlvFields.put(1312, "PowerSet");
        tlvFields.put(1313, "WarningLevel");
        tlvFields.put(1314, "BlockingLevel");
        tlvFields.put(1315, "ShuttingLevel");
        tlvFields.put(1004, "SerialNo");
        tlvFields.put(32770, "PossessorLinks");
        tlvFields.put(32772, "PossessorLink");
        tlvFields.put(1009, "PossessorRef");
        tlvFields.put(32773, "TerminalProfileLinks");
        tlvFields.put(32778, "TerminalProfileLink");
        tlvFields.put(1011, "TerminalProfileRef");
        tlvFields.put(32774, "RegisteredTerminals");
        tlvFields.put(32775, "RegisteredTerminal");
        tlvFields.put(1012, "VirtualPOSID");
        tlvFields.put(1453, "PosIDAddress");
        tlvFields.put(1482, "PosIDPossessorName");
        tlvFields.put(1113, "AcquiringRef");
        tlvFields.put(1180, "KeyTemplateRef");
        tlvFields.put(32881, "SecurityKeys");
        tlvFields.put(32835, "WKey");
        tlvFields.put(1109, "KeyProfile");
        tlvFields.put(1111, "KeyValue");
        tlvFields.put(1178, "UseLogins");
        tlvFields.put(1199, "TypeUseLogins");
        tlvFields.put(1179, "AutoLogin");
        tlvFields.put(1310, "AutoLoginUsername");
        tlvFields.put(1006, "LogoutCase");
        tlvFields.put(1007, "CardLoginPassword");
        tlvFields.put(32864, "TerminalEvents");
        tlvFields.put(32865, "TerminalEvent");
        tlvFields.put(1156, "EventType");
        tlvFields.put(1157, "Periodicity");
        tlvFields.put(1158, "EffectiveTime");
        tlvFields.put(1159, "ExpiryTime");
        tlvFields.put(1160, "WeekDays");
        tlvFields.put(1161, "MonthDays");
        tlvFields.put(1162, "EventDate");
        tlvFields.put(1398, "EventPeriod");
        tlvFields.put(1171, "CombineFunctions");
        tlvFields.put(1417, "ResetReceipt");
        tlvFields.put(1339, "TransactionScheme");
        tlvFields.put(1340, "HotKeysProfile");
        tlvFields.put(1349, "PicturesGroupTypeSelectedTerminal");
        tlvFields.put(1350, "PicturesGroupSelectedTerminal");
        tlvFields.put(1422, "ReportRepeat");
        tlvFields.put(1522, "PromptPrintDecline");
        tlvFields.put(1477, "WaitPinTimeout");
        tlvFields.put(1005, "Description");
        tlvFields.put(1384, "TerminalVariable");
        tlvFields.put(1538, "UseEnergySaving");
        tlvFields.put(1468, "BackLightTimeout");
        tlvFields.put(1469, "SleepTime");
        tlvFields.put(1470, "HibernateTimeout");
        tlvFields.put(1471, "ShutdownTimeout");
        tlvFields.put(1534, "KeyboardLock");
    }
}
