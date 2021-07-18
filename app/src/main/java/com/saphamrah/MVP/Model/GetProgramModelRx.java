/**
 * //<string-array name="getProgramItemsRx">
 * //<item>{\"name\":\"بانک\" , \"function\":\"getBank\"}</item>   {@link GetProgramModelRx#updateBankTable(ArrayList)}
 * //<item>{\"name\":\"برند\" , \"function\":\"getBrand\"}</item> {@link GetProgramModelRx#updateBrandTable(ArrayList)}
 * //<item>{\"name\":\"علت عدم درخواست\" , \"function\":\"getElatAdamDarkhast\"}</item> {@link GetProgramModelRx#updateElatAdamDarkhastTable(ArrayList)}
 * //<item>{\"name\":\"علت مرجوعی کالا\" , \"function\":\"getElatMarjoeeKala\"}</item> {@link GetProgramModelRx#updateElatMarjoeeKalaTable(ArrayList)}
 * //<item>{\"name\":\"گروه\" , \"function\":\"getGoroh\"}</item> {@link GetProgramModelRx#updateGorohTable(int, ArrayList)}
 * //<item>{\"name\":\"اطلاعات GPS\" , \"function\":\"getGPSData\"}</item>{@link GetProgramModelRx#deleteGPSDataMashin(int)}
 * //<item>{\"name\":\"مناطق جغرافیایی\" , \"function\":\"getMahal\"}</item>{@link GetProgramModelRx#updateMahalTable(ArrayList)}
 * //<item>{\"name\":\"مرکز فروش\" , \"function\":\"getMarkazAnbar\"}</item>{@link GetProgramModelRx#updateMarkazAnbarTable(ArrayList)}
 * //<item>{\"name\":\"مرکز انبار\" , \"function\":\"getMarkazForosh\"}</item>{@link GetProgramModelRx#updateMarkazForoshTable(ArrayList)}
 * //<item>{\"name\":\"شماره حساب مرکز پخش\" , \"function\":\"getMarkazPakhshShomareHesab\"}</item>{@link GetProgramModelRx#updateMarkazShomarehHesabTable(ArrayList)}
 * //<item>{\"name\":\"اطلاعات POS\" , \"function\":\"getPOSInfo\"}</item>{@link GetProgramModelRx#updatePosShomarehHesabTable(ArrayList)}
 * //<item>{\"name\":\"اطلاعات مسیر\" , \"function\":\"getMasir\"}</item>{@link GetProgramModelRx#updateAllMasirTable(int, ArrayList)}
 * //<item>{\"name\":\"محدوده فروشنده\" , \"function\":\"foroshandehPolygon\"}</item>{@link GetProgramModelRx#updateForoshandehPolygonTable(int, ArrayList)}
 * //<item>{\"name\":\"مسیر وزن حجم\" , \"function\":\"getMasirVazHajm\"}</item> {@link GetProgramModelRx#updateMasirVaznHajmMashinTable(int, ArrayList)}
 * //<item>{\"name\":\"موجودی گیری\" , \"function\":\"deleteMojodiGiri\"}</item>{@link GetProgramModelRx#deleteMojodiGiriRx(int)}
 * //<item>{\"name\":\"مشتری\" , \"function\":\"getMoshtary\"}</item>{@link GetProgramModelRx#updateMoshtaryTable(int, ArrayList)}
 * //<item>{\"name\":\"آدرس پرنت\" , \"function\":\"getMoshtaryParent\"}</item>{@link GetProgramModelRx#updateMoshtaryTableWithParent(ArrayList)}
 * //<item>{\"name\":\"آدرس مشتری\" , \"function\":\"getMoshtaryAddress\"}</item> {@link GetProgramModelRx#updateMoshtaryAddressTable(ArrayList)}
 * //<item>{\"name\":\"مشتری افراد\" , \"function\":\"getMoshtaryAfrad\"}</item>{@link GetProgramModelRx#updateMoshtaryAfradTable(ArrayList)}
 * //<item>{\"name\":\"اعتبار مشتری\" , \"function\":\"getMoshtaryEtebarSazmanForosh\"}</item>{@link GetProgramModelRx#updateMoshtaryEtebatSazmanForoshTable(int, ArrayList)}
 * //<item>{\"name\":\"گروه مشتری\" , \"function\":\"getMoshtaryGoroh\"}</item>{@link GetProgramModelRx#updateMoshtaryGorohTable(int)}
 * //<item>{\"name\":\"محدوده مشتری\" , \"function\":\"getMoshtaryPolygon\"}</item>{@link GetProgramModelRx#updateMoshtaryPolygonTable(ArrayList)}
 * //<item>{\"name\":\"شماره حساب های مشتری\" , \"function\":\"getMoshtaryShomareHesab\"}</item>{@link GetProgramModelRx#updateMoshtaryShomarehHesabTable(int, ArrayList)}
 * //<item>{\"name\":\"مشتری تغییرات\" , \"function\":\"deleteMoshtaryTaghirat\"}</item>{@link GetProgramModelRx#deleteMoshtaryTaghiratRx(int)}
 * //<item>{\"name\":\" فاکتور\" , \"function\":\"getDarkhastFaktor\"}</item>{@link GetProgramModelRx#updateDarkhastFaktorTable(ArrayList)}
 * //<item>{\"name\":\" بروزرسانی سازمان مشتریان\" , \"function\":\"updateSazmanMoshtarian\"}</item>{@link GetProgramModelRx#updateSazmanMoshtarian(DarkhastFaktorModel)}
 * //<item>{\"name\":\"درخواست فاکتور سطر\" , \"function\":\"getDarkhastFaktorSatr\"}</item>{@link GetProgramModelRx#updateDarkhastFaktorSatrTable(boolean, int, Map, ArrayList)}
 * //<item>{\"name\":\"فروشنده ها - پخش\" , \"function\":\"getForoshandehsPakhsh\"}</item>{@link GetProgramModelRx#updateForoshandeTable(int, ArrayList, ArrayList)}
 * //<item>{\"name\":\"کالای پیشنهادی \" , \"function\":\"getdarkhastFaktorKalaPishnahadi\"}</item>{@link GetProgramModelRx#updateDarkhastFaktorKalaPishnahadiTable(ArrayList)}
 * //<item>{\"name\":\"کالای مرجوعی \" , \"function\":\"getKalaMarjoee\"}</item>{@link GetProgramModelRx#updateListKalaForMarjoeeTable(ArrayList)}
 * //<item>{\"name\":\"گزارش برگشتی\" , \"function\":\"getBargashty\"}</item>{@link GetProgramModelRx#updateBargashtyTable(int, ArrayList)}
 * //<item>{\"name\":\"مشتری - پخش\" , \"function\":\"getMoshtaryPakhsh\"}</item>{@link GetProgramModelRx#updateMoshtaryPakhshTable(ArrayList)}
 * //<item>{\"name\":\"مشتری مراجعه شده\" , \"function\":\"getMoshtaryMorajeShodehRooz\"}</item>{@link GetProgramModelRx#updateMoshtaryMorajeShodeRoozTable(int, ArrayList)}
 * //<item>{\"name\":\"آدرس مشتری - پخش\" , \"function\":\"getMoshtaryAddressPakhsh\"}</item>{@link GetProgramModelRx#updateMoshtaryAddressMamorPakhshTable(int, ArrayList, ArrayList)}
 * //<item>{\"name\":\"گروه مشتری - پخش\" , \"function\":\"getMoshtaryGorohPakhsh\"}</item>{@link GetProgramModelRx#updateMoshtaryGorohTable(int)}
 * //<item>{\"name\":\"مشتری افراد - پخش\" , \"function\":\"getMoshtaryAfradPakhsh\"}</item>{@link GetProgramModelRx#updateMoshtaryAfradPakhshTable(ArrayList)}
 * //<item>{\"name\":\"شماره حساب مشتری - پخش\" , \"function\":\"getMoshtaryShomareHesabPakhsh\"}</item></item>{@link GetProgramModelRx#updateMoshtaryShomarehHesabTable(int, ArrayList)}
 * //<item>{\"name\":\"اعتبار مشتری - پخش\" , \"function\":\"getMoshtaryEtebarPakhsh\"}</item>{@link GetProgramModelRx#updateMoshtaryEtebarSazmanForoshPakhshTable(int, ArrayList)}
 * //<item>{\"name\":\"دریافت پرداخت\" , \"function\":\"getDariaftPardakht\"}</item>{@link GetProgramModelRx#updateDariaftPardakhtPPCTable(int, String, ArrayList)}
 * //<item>{\"name\":\"دریافت پرداخت - درخواست فاکتور\" , \"function\":\"getDariaftPardakhtDarkhastFaktor\"}</item>{@link GetProgramModelRx#updateDariaftPardakhtDarkhastFaktorTable(int, ArrayList)}
 * //<item>{\"name\":\"دریافت تخفیفات اقلام فاکتور\" , \"function\":\"getDarkhastFaktorSatrTakhfif\"}</item>{@link GetProgramModelRx#updateDarkhastFaktorSatrTable(boolean, int, Map, ArrayList)}
 * //<item>{\"name\":\"دریافت تخفیفات فاکتور\" , \"function\":\"getDarkhastFaktorTakhfif\"}</item>{@link GetProgramModelRx#updateDarkhastFaktorSatrTakhfifTable(int)} , int, Map, ArrayList)}
 * //<item>{\"name\":\"مدت وصول\" , \"function\":\"getModatVosol\"}</item>{@link GetProgramModelRx#updateModatVosolTable(int, ArrayList)} , int, Map, ArrayList)}
 * //<item>{\"name\":\"گروه مدت وصول\" , \"function\":\"getModatVosolGoroh\"}</item>{@link GetProgramModelRx#updateModatVosolGorohTable(ArrayList)} , int, Map, ArrayList)}
 * //<item>{\"name\":\"مدت وصول مرکز\" , \"function\":\"getModatVosolMarkaz\"}</item>{@link GetProgramModelRx#updateModatVosolMarkazTable(ArrayList)} , int, Map, ArrayList)}
 * //<item>{\"name\":\"انبارک\" , \"function\":\"getAnbarakInfo\"}</item>{@link GetProgramModelRx#updateAnbarakInfoTable(int, ArrayList)} , int, Map, ArrayList)}
 * //<item>{\"name\":\"اعتبارات\" , \"functdion\":\"getEtebar\"}</item>{@link GetProgramModelRx#getEtebarRx(int)} }
 * //<item>{\"name\":\"اعتبار فروشنده\" , \"functdion\":\"getEtebarForoshandeh\"}</item>{@link GetProgramModelRx#updateEtebarForoshandehTable(int, ArrayList)} }
 * //<item>{\"name\":\"کالا\" , \"function\":\"getKala\"}</item>{@link GetProgramModelRx#updateKalaTable(int, ArrayList, ArrayList, ArrayList)} }
 * //<item>{\"name\":\"اولویت کالا\" , \"function\":\"getKalaolaviat\"}</item>{@link GetProgramModelRx#updateKalaOlaviatTable(int, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)} }
 * //<item>{\"name\":\"موجودی انبارک\" , \"function\":\"getAnbarakMojodi\"}</item>{@link GetProgramModelRx#updateAnbarakInfoTable(int, ArrayList)} }
 * //<item>{\"name\":\"کالا موجودی\" , \"function\":\"getKalaMojodi\"}</item>{@link GetProgramModelRx#updateKalaMojodiTable(int, ArrayList, ArrayList, ArrayList, ArrayList)} }
 * //<item>{\"name\":\"گروه کالا\" , \"function\":\"getKalaGoroh\"}</item>{@link GetProgramModelRx#updateKalaGorohTable(int, ArrayList, ArrayList)} }
 * //<item>{\"name\":\"ضریب فروش کالا\" , \"function\":\"getZaribForoshKala\"}</item>{@link GetProgramModelRx#updateKalaZaribForoshTable(int, ArrayList)} }
 * //<item>{\"name\":\"جوایز\" , \"function\":\"getJayezeh\"}</item>{@link GetProgramModelRx#updateJayezehTable(ArrayList)} }
 * //<item>{\"name\":\"جایزه انتخابی\" , \"function\":\"getJayezehSatr\"}</item>{@link GetProgramModelRx#updateJayezehEntekhabiTable(int, ArrayList)} }
 * //<item>{\"name\":\"مبلغ فروش جایزه\" , \"function\":\"getJayezeh\"}</item>{@link GetProgramModelRx#updateMablagheForoshJayeze(ArrayList)} }
 * //<item>{\"name\":\"اقلام جوایز \" , \"function\":\"getJayezehSatr\"}</item>{@link GetProgramModelRx#updateJayezehSatrTable(ArrayList)} }
 * //<item>{\"name\":\"نوع حساب\" , \"function\":\"getNoeHesab\"}</item>{@link GetProgramModelRx#updateNoeHesabTable(ArrayList)} }
 * //<item>{\"name\":\"نوع مالکیت مشتری\" , \"function\":\"getNoeMalekiatMoshtary\"}</item>{@link GetProgramModelRx#updateNoeMalekiatMoshtaryTable(ArrayList)} }
 * //<item>{\"name\":\"تعداد فاکتور مشتری\" , \"function\":\"getTedadFaktorMoshtary\"}</item>{@link GetProgramModelRx#updateTedadFaktorMoshtaryTable(ArrayList)} }
 * //<item>{\"name\":\"تخفیفات حجمی\" , \"function\":\"getTakhfifHajmi\"}</item>{@link GetProgramModelRx#updateTakhfifHajmiTable(ArrayList)} }
 * //<item>{\"name\":\"تخفیف نقدی\" , \"function\":\"getTakhfifNaghdi\"}</item>{@link GetProgramModelRx#updateTakhfifNaghdiTable(ArrayList)} }
 * //<item>{\"name\":\"تخفیف صنفی\" , \"function\":\"getTakhfifSenfi\"}</item>{@link GetProgramModelRx#updateTakhfifSenfiTable(ArrayList)} }
 * //<item>{\"name\":\"اقلام تخفیفات حجمی\" , \"function\":\"getTakhfifHajmiSatr\"}</item>{@link GetProgramModelRx#updateTakhfifHajmiSatrTable(ArrayList)} }
 * //<item>{\"name\":\"اقلام تخفیف صنفی\" , \"function\":\"getTakhfifSenfiSatr\"}</item>{@link GetProgramModelRx#updateTakhfifSenfiSatrTable(ArrayList)} }
 * //<item>{\"name\":\"تقویم تعطیلی\" , \"function\":\"getTaghvimTatil\"}</item>{@link GetProgramModelRx#updateTaghvimTatilTable(int, ArrayList, ArrayList)} }
 * //<item>{\"name\":\"نوع وصول\" , \"function\":\"getCodeNoeVosol\"}</item>{@link GetProgramModelRx#updateCodeNoeVosolTable(int, ArrayList)} }
 * //<item>{\"name\":\"کاردکس\" , \"function\":\"deleteKardex\"}</item>{@link GetProgramModelRx#deleteKardexAndKardexSatrTable(int)} }
 * //<item>{\"name\":\"کاردکس - سطر\" , \"function\":\"deleteKardexSatr\"}</item>{@link GetProgramModelRx#deleteKardexAndKardexSatrTable(int)} }
 * //<item>{\"name\":\"عکس مرجوعی کامل\" , \"function\":\"deleteMarjoeeKamelImage\"}</item>{@link GetProgramModelRx#deleteMarjoeeKamelImageRx(int)} }
 * //<item>{\"name\":\"گزارش موجودی انبار\" , \"function\":\"getRptMojodyAnbar\"}</item> {removed from getProgram}
 * //<item>{\"name\":\"گزارش کالا\" , \"function\":\"getRptKalaInfo\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش فاکتورهای توزیع نشده\" , \"functdion\":\"getRptFaktorTozieNashode\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش ویزیت فروشنده\" , \"functdion\":\"getRptForoshandehVisit\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"تبلیغات\" , \"functdion\":\"getTizerTablighat\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"تنظیمات سیستم\" , \"functdion\":\"getSystemConfigTablet\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش لیست وصول\" , \"functdion\":\"getRptListVosol\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش خرید کالا\" , \"functdion\":\"getRptKharidKala\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش مسیر فروشنده\" , \"functdion\":\"getRptMasir\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"ورژن\" , \"functdion\":\"getRptVersionInfo\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"مشتری چیدمان\" , \"functdion\":\"getMoshtaryChidman\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"لیست مشتریان فروشنده\" , \"functdion\":\"getAllMoshtaryForoshandeh\"}</item>{removed from getProgram}
 * //<item>{\"name\":\"گزارش فروش\" , \"functdion\":\"getRptForosh\"}</item>{@link GetProgramModelRx#updateRptAmarForoshTable(ArrayList)} }
 * //<item>{\"name\":\"گزارش مانده دار\" , \"functdion\":\"getRptMandehDar\"}</item>{@link GetProgramModelRx#updateRptMandehDarTable(ArrayList)} }
 * //<item>{\"name\":\"گزارش اسناد\" , \"functdion\":\"getRptAsnad\"}</item>{@link GetProgramModelRx#updateRptSanadTable(ArrayList)} }
 * //<item>{\"name\":\"گزارش هدف فروش\" , \"functdion\":\"getRptHadaf\"}</item>{@link GetProgramModelRx#updateRptHadafForoshTable(ArrayList)} }
 * //<item>{\"name\":\"اعلام مرجوعی\" , \"function\":\"getElamMarjoee\"}</item>{@link GetProgramModelRx#updateMarjoeeTitrTable(ArrayList)} }
 * //<item>{\"name\":\"اعلام مرجوعی سطر\" , \"function\":\"getElamMarjoeeSatr\"}</item>{@link GetProgramModelRx#updateMarjoeeSatrTable(ArrayList)} }
 * //<item>{\"name\":\"اقلام اعلام مرجوعی\" , \"function\":\"deleteElamMarjoeeTedad\"}</item>{@link GetProgramModelRx#deleteElamMarjoeeTedadRx(int)} }
 * //<item>{\"name\":\"اعتبار پیشفرض\" , \"functdion\":\"getMoshtaryEtebarPishfarz\"}</item>{@link GetProgramModelRx#updateMoshtaryEtebarPishfarzTable(ArrayList)} }
 * //<item>{\"name\":\"اطلاعات درخواست مشتری جدید\" , \"functdion\":\"getDarkhastFaktorMoshtaryJadid\"}</item>{@link GetProgramModelRx#updateMoshtaryJadidDarkhastTable(ArrayList)} }
 * //<item>{\"name\":\"گروه کالا بر اساس صنف\" , \"functdion\":\"getGorohKala\"}</item>{@link GetProgramModelRx#updateGorohKalaNoeSenfTable(ArrayList)} }
 * //<item>{\"name\":\"تفکیک جز\" , \"functdion\":\"getTafkikJozePakhsh\"}</item>{@link GetProgramModelRx#updateTafkikJozeTable(int, ArrayList)} }
 * //<item>{\"name\":\"شهر\" , \"functdion\":\"getAllMarkazShahrMarkazi\"}</item>{@link GetProgramModelRx#updateMarkazShahrMarkaziTable(ArrayList)} }
 * //<item>{\"name\":\"حداقل ریال خرید\" , \"functdion\":\"getAllNoeMoshtaryRialKharid\"}</item>{@link GetProgramModelRx#updateNoeMoshtaryRialKharidTable(ArrayList)} }
 * //<item>{\"name\":\"کدپستی\" , \"functdion\":\"getAllcodePosti\"}</item>{@link GetProgramModelRx#updateMahalCodePostiTable(ArrayList)} }
 * //<item>{\"name\":\"نوع وصول\" , \"functdion\":\"getNoeVosolMoshtary\"}</item>{@link GetProgramModelRx#updateCodeNoeVosolTable(int, ArrayList)} }
 * //<item>{\"name\":\"مشتری برند\" , \"functdion\":\"getMoshtaryBrand\"}</item>{@link GetProgramModelRx#updateMoshtaryBrandTable(ArrayList)} }
 * //<item>{\"name\":\"پشتیبانی آنلاین\" , \"functdion\":\"getSupportCrisp\"}</item>{@link GetProgramModelRx#updateCrispTable(int, ArrayList)} }
 * //<item>{\"name\":\"قرارداد مشتری\" , \"functdion\":\"getAllMoshtaryGharardad\"}</item>{@link GetProgramModelRx#updateMoshtaryGhararadTable(int, ArrayList)} }
 * //<item>{\"name\":\"کالا مصوبه قرارداد\" , \"functdion\":\"getAllKalaMosavab\"}</item>{@link GetProgramModelRx#updateMoshtaryGharardadKalaTable(int, ArrayList)} }
 * //<item>{\"name\":\"دریافت تنظیمات\" , \"functdion\":\"getParameter\"}</item>{@link GetProgramModelRx#updateParameter(ForoshandehMamorPakhshModel)} {@link GetProgramModelRx#updateParameterChildTable(ArrayList)}
 * //<item>{\"name\":\"برخورد فروشنده با مشتری\" , \"function\":\"getBarkhordForoshandehBaMoshtary\"}</item>{@link GetProgramModelRx#updateBarkhordForoshandehBaMoshtaryTable(int, ArrayList)}
 * //</string-array>
 **/


package com.saphamrah.MVP.Model;


import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.GetProgramMVP;
import com.saphamrah.DAO.AllMoshtaryForoshandehDAO;
import com.saphamrah.DAO.AmargarGorohDAO;
import com.saphamrah.DAO.AmargarMarkazSazmanForoshDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.DAO.BrandDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCTedadDAO;
import com.saphamrah.DAO.ElatAdamMoarefiMoshtaryDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehEtebarDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.DAO.GorohKalaNoeSenfDAO;
import com.saphamrah.DAO.ImageDarkhastFaktorDAO;
import com.saphamrah.DAO.JayezehDAO;
import com.saphamrah.DAO.JayezehEntekhabiDAO;
import com.saphamrah.DAO.JayezehSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.KalaOlaviatDAO;
import com.saphamrah.DAO.KalaZaribForoshDAO;
import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.DAO.MahalCodePostiDAO;
import com.saphamrah.DAO.MahalDAO;
import com.saphamrah.DAO.MandehMojodyMashinDAO;
import com.saphamrah.DAO.MarjoeeKamelImageDAO;
import com.saphamrah.DAO.MarjoeeKamelImageTmpDAO;
import com.saphamrah.DAO.MarkazDAO;
import com.saphamrah.DAO.MarkazShahrMarkaziDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.ModatVosolDAO;
import com.saphamrah.DAO.ModatVosolGorohDAO;
import com.saphamrah.DAO.ModatVosolMarkazDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryAmargarImageDAO;
import com.saphamrah.DAO.MoshtaryAmargarImageTmpDAO;
import com.saphamrah.DAO.MoshtaryBrandDAO;
import com.saphamrah.DAO.MoshtaryChidmanDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarPishFarzDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryJadidDarkhastDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.DAO.NoeFaaliatForMoarefiMoshtaryJadidDAO;
import com.saphamrah.DAO.NoeHesabDAO;
import com.saphamrah.DAO.NoeMalekiatMoshtaryDAO;
import com.saphamrah.DAO.NoeMoshtaryRialKharidDAO;
import com.saphamrah.DAO.NoeTablighatDAO;
import com.saphamrah.DAO.NoeVosolMoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.ParameterDAO;
import com.saphamrah.DAO.PorseshnamehDAO;
import com.saphamrah.DAO.PorseshnamehShomareshDAO;
import com.saphamrah.DAO.PorseshnamehTablighatDAO;
import com.saphamrah.DAO.PorseshnamehTozihatDAO;
import com.saphamrah.DAO.RotbehDAO;
import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.DAO.RptMarjoeeDAO;
import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.DAO.SupportCrispDAO;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.DAO.TafkikJozeDAO;
import com.saphamrah.DAO.TaghiratVersionPPCDAO;
import com.saphamrah.DAO.TaghvimTatilDAO;
import com.saphamrah.DAO.TakhfifHajmiDAO;
import com.saphamrah.DAO.TakhfifHajmiSatrDAO;
import com.saphamrah.DAO.TakhfifNaghdyDAO;
import com.saphamrah.DAO.TakhfifSenfiDAO;
import com.saphamrah.DAO.TakhfifSenfiSatrDAO;
import com.saphamrah.DAO.TedadFaktorMoshtaryDAO;
import com.saphamrah.DAO.VisitMoshtaryDAO;
import com.saphamrah.MVP.View.GetProgramActivity;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.GetImageStringModel;
import com.saphamrah.Model.GorohKalaNoeSenfModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.JayezehModel;
import com.saphamrah.Model.JayezehSatrModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.MarkazModel;
import com.saphamrah.Model.MarkazShahrMarkaziModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Model.ModatVosolGorohModel;
import com.saphamrah.Model.ModatVosolMarkazModel;
import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryBrandModel;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.Model.MoshtaryEtebarPishFarzModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryJadidDarkhastModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.Model.MoshtaryParentModel;
import com.saphamrah.Model.NoeFaaliatForMoarefiMoshtaryJadidModel;
import com.saphamrah.Model.NoeMoshtaryRialKharidModel;
import com.saphamrah.Model.NoeVosolMoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ParameterModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.SupportCrispModel;
import com.saphamrah.Model.TafkikJozeModel;
import com.saphamrah.Model.TaghiratVersionPPCModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.Model.TakhfifHajmiModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifSenfiModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.DeviceInfo;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.Logger;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.LastOlaviatMoshtaryShared;
import com.saphamrah.Shared.LocalConfigShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.Utils.CollectionUtils;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxDAOUtils;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.Repository.AdamDarkhastRepository;
import com.saphamrah.Repository.AllMoshtaryForoshandehRepository;
import com.saphamrah.Repository.AnbarakAfradRepository;
import com.saphamrah.Repository.BankRepository;
import com.saphamrah.Repository.BargashtyRepository;
import com.saphamrah.Repository.BarkhordForoshandehBaMoshtaryRepository;
import com.saphamrah.Repository.BrandRepository;
import com.saphamrah.Repository.ConfigNoeVosolMojazeFaktorRepository;
import com.saphamrah.Repository.ConfigNoeVosolMojazeMoshtaryRepository;
import com.saphamrah.Repository.DariaftPardakhtDarkhastFaktorPPCRepository;
import com.saphamrah.Repository.DariaftPardakhtPPCRepository;
import com.saphamrah.Repository.DarkhastFaktorEmzaMoshtaryRepository;
import com.saphamrah.Repository.DarkhastFaktorKalaPishnahadiRepository;
import com.saphamrah.Repository.DarkhastFaktorRepository;
import com.saphamrah.Repository.DarkhastFaktorRoozSortRepository;
import com.saphamrah.Repository.DarkhastFaktorSatrRepository;
import com.saphamrah.Repository.DarkhastFaktorSatrTakhfifRepository;
import com.saphamrah.Repository.DarkhastFaktorTakhfifRepository;
import com.saphamrah.Repository.ElamMarjoeePPCRepository;
import com.saphamrah.Repository.ElamMarjoeeSatrPPCRepository;
import com.saphamrah.Repository.ElamMarjoeeSatrPPCTedadRepository;
import com.saphamrah.Repository.ElatAdamDarkhastRepository;
import com.saphamrah.Repository.ElatMarjoeeKalaRepository;
import com.saphamrah.Repository.EtebarRepository;
import com.saphamrah.Repository.ForoshandehEtebarRepository;
import com.saphamrah.Repository.ForoshandehMamorPakhshRepository;
import com.saphamrah.Repository.ForoshandehRepository;
import com.saphamrah.Repository.GPSDataMashinRepository;
import com.saphamrah.Repository.GPSDataPpcRepository;
import com.saphamrah.Repository.GorohKalaNoeSenfRepository;
import com.saphamrah.Repository.GorohRepository;
import com.saphamrah.Repository.JayezehEntekhabiRepository;
import com.saphamrah.Repository.JayezehRepository;
import com.saphamrah.Repository.JayezehSatrRepository;
import com.saphamrah.Repository.KalaGorohRepository;
import com.saphamrah.Repository.KalaMojodiRepository;
import com.saphamrah.Repository.KalaOlaviatRepository;
import com.saphamrah.Repository.KalaRepository;
import com.saphamrah.Repository.KalaZaribForoshRepository;
import com.saphamrah.Repository.KardexRepository;
import com.saphamrah.Repository.KardexSatrRepository;
import com.saphamrah.Repository.ListKalaForMarjoeeRepository;
import com.saphamrah.Repository.LogPPCRepository;
import com.saphamrah.Repository.MahalCodePostiRepository;
import com.saphamrah.Repository.MahalRepository;
import com.saphamrah.Repository.MandehMojodyMashinRepository;
import com.saphamrah.Repository.MarjoeeKamelImageRepository;
import com.saphamrah.Repository.MarjoeeMamorPakhshRepository;
import com.saphamrah.Repository.MarkazRepository;
import com.saphamrah.Repository.MarkazShahrMarkaziRepository;
import com.saphamrah.Repository.MarkazShomarehHesabRepository;
import com.saphamrah.Repository.MasirRepository;
import com.saphamrah.Repository.MasirVaznHajmMashinRepository;
import com.saphamrah.Repository.ModatVosolGorohRepository;
import com.saphamrah.Repository.ModatVosolMarkazRepository;
import com.saphamrah.Repository.ModatVosolRepository;
import com.saphamrah.Repository.MojoodiGiriRepository;
import com.saphamrah.Repository.MoshtaryAddressRepository;
import com.saphamrah.Repository.MoshtaryAfradRepository;
import com.saphamrah.Repository.MoshtaryBrandRepository;
import com.saphamrah.Repository.MoshtaryChidmanRepository;
import com.saphamrah.Repository.MoshtaryEtebarPishFarzRepository;
import com.saphamrah.Repository.MoshtaryEtebarSazmanForoshRepository;
import com.saphamrah.Repository.MoshtaryGharardadKalaRepository;
import com.saphamrah.Repository.MoshtaryGharardadRepository;
import com.saphamrah.Repository.MoshtaryJadidDarkhastRepository;
import com.saphamrah.Repository.MoshtaryMorajehShodehRoozRepository;
import com.saphamrah.Repository.MoshtaryPolygonRepository;
import com.saphamrah.Repository.MoshtaryRepository;
import com.saphamrah.Repository.MoshtaryShomarehHesabRepository;
import com.saphamrah.Repository.MoshtaryTaghiratRepository;
import com.saphamrah.Repository.NoeFaaliatForMoarefiMoshtaryJadidRepository;
import com.saphamrah.Repository.NoeHesabRepository;
import com.saphamrah.Repository.NoeMalekiatMoshtaryRepository;
import com.saphamrah.Repository.NoeMoshtaryRialKharidRepository;
import com.saphamrah.Repository.NoeVosolMoshtaryRepository;
import com.saphamrah.Repository.ParameterChildRepository;
import com.saphamrah.Repository.ParameterRepository;
import com.saphamrah.Repository.PolygonForoshSatrRepository;
import com.saphamrah.Repository.PosShomarehHesabRepository;
import com.saphamrah.Repository.RptDarkhastFaktorVazeiatPPCRepository;
import com.saphamrah.Repository.RptForoshRepository;
import com.saphamrah.Repository.RptHadafForoshRepository;
import com.saphamrah.Repository.RptMandehdarRepository;
import com.saphamrah.Repository.RptSanadRepository;
import com.saphamrah.Repository.SupportCrispRepository;
import com.saphamrah.Repository.TafkikJozeRepository;
import com.saphamrah.Repository.TaghiratVersionPPCRepository;
import com.saphamrah.Repository.TaghvimTatilRepository;
import com.saphamrah.Repository.TakhfifHajmiRepository;
import com.saphamrah.Repository.TakhfifHajmiSatrRepository;
import com.saphamrah.Repository.TakhfifNaghdyRepository;
import com.saphamrah.Repository.TakhfifSenfiRepository;
import com.saphamrah.Repository.TakhfifSenfiSatrRepository;
import com.saphamrah.Repository.TedadFaktorMoshtaryRepository;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.ServiceResponse.BarkhordForoshandehBaMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.DarkhastFaktorKalaPishnahadiResult;
import com.saphamrah.WebService.ServiceResponse.GetAllGorohResult;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryByccMasirResult;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadKalaResult;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadResult;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeHesabResult;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeMalekiatMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.GetAllTaghvimTatilByccMarkazResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvForoshandehByccForoshandehResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvJayezehSatrResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryAddressResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryAfradResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryEtebarSazmanForoshResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryShomarehHesabResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifHajmiByccMarkazForoshResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifNaghdyByccMarkazForoshResult;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifSenfiByccMarkazForoshResult;
import com.saphamrah.WebService.ServiceResponse.GetAnbarakAfradResult;
import com.saphamrah.WebService.ServiceResponse.GetConfigNoeVosolMojazeMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.GetConfigNoeVosolMojazefaktorResult;
import com.saphamrah.WebService.ServiceResponse.GetDariaftPardakhtDarkhastFaktorHavalehPPCResult;
import com.saphamrah.WebService.ServiceResponse.GetDariaftPardakhtHavalePPCResult;
import com.saphamrah.WebService.ServiceResponse.GetDarkhastFaktorResult;
import com.saphamrah.WebService.ServiceResponse.GetDarkhastFaktorSatrResult;
import com.saphamrah.WebService.ServiceResponse.GetEtebarForoshandehResult;
import com.saphamrah.WebService.ServiceResponse.GetKalaOlaviatResult;
import com.saphamrah.WebService.ServiceResponse.GetListBargashtyForoshandehResult;
import com.saphamrah.WebService.ServiceResponse.GetListKalaForMarjoeeResult;
import com.saphamrah.WebService.ServiceResponse.GetMairVaznHajmMashinResult;
import com.saphamrah.WebService.ServiceResponse.GetMarjoeeForoshandehByDarkhastFaktorSatrResult;
import com.saphamrah.WebService.ServiceResponse.GetMarjoeeForoshandehByDarkhastFaktorTitrResult;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryPolygonResult;
import com.saphamrah.WebService.ServiceResponse.GetParameterChildResult;
import com.saphamrah.WebService.ServiceResponse.GetParameterResult;
import com.saphamrah.WebService.ServiceResponse.GetTafkikJozePakhshResult;
import com.saphamrah.WebService.ServiceResponse.GetTedadFaktorMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.MarjoeeMamorPakhshResult;
import com.saphamrah.WebService.ServiceResponse.SupportCrispResult;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetProgramModelRx implements GetProgramMVP.ModelOps {
    public static final String CLASS_NAME = "GetProgramModelRx";
    public static final String ACTIVITY_NAME = "GetProgramActivity";


    //for logging exceptions
    private Logger logger;

    ArrayList<String> listCodeMoshtary = new ArrayList<>();
    ArrayList<Integer> listccforoshandeh = new ArrayList<>();
    private CompositeDisposable compositeDisposable;

    private GetProgramMVP.RequiredPresenterOps mPresenter;

    private ForoshandehMamorPakhshModel foroshandehMamorPakhshModel;
    private int ccForoshandeh = 0;
    private int ccMamorPakhsh = 0;
    private int ccAfrad = 0;
    private int ccMarkazForosh = 0;
    private String ccMarkazForoshPakhsh = "-1";
    private int ccPosShomarehHesab = 0;
    private String ccTakhfifHajmis = "-1";
    private Set<String> ccTakhfifHajmiList ;
    private String ccTakhfifSenfis = "-1";
    private Set<String> ccTakhfifSenfiList ;
    private int ccMarkazAnbar = 0;
    private int ccMarkazSazmanForoshSakhtarForosh = 0;

    private int ccMarkazSazmanForosh = 0;
    private String ccMarkazSazmanForoshPakhsh = "-1";
    private int ccSazmanForosh = 0;
    private String ccSazmanForoshPakhsh = "-1";
    private String ccMasirs = "-1";
    private String ccMoshtarys = "-1";
    private String ccJayezehs = "-1";
    private Set<String> ccJayezehList ;
    private AtomicReference<String> ccGorohs = new AtomicReference<>();
    private String ccGorohss;
//    AtomicReference<String> ccMarkazSazmanSakhtarForosh = new AtomicReference<>();


    private String ccDarkhastFaktors = "-1";
    private String ccDarkhastFaktorPakhsh = "-1";
    private String ccMoshtaryPakhsh = "-1";
    private String ccForoshandehString = "-1";
    private String ccDariaftPardakhts = "";

    private String ccAfradPakhsh = "-1";

    String[] foroshandehArray;
    private String anbarakAfrad = "-1";
    private int noeMasouliat = -1;
    private Calendar calendar;
    private String date;
    private String selectedDateGregorian;
    private String activityNameForLog = "GetProgramActivity";
    private int itemCounter;

    private int requestCounter;
    private int getProgramItemCount = 0; //count of all item that exist in string-array of resources
    private Handler handler;


    private ArrayList<String> ccMasirList = new ArrayList<>();
    private ArrayList<String> ccMoshtaryList = new ArrayList<>();

    //GET WEBSERVICE
    private ServerIpModel serverIpModel;
    private APIServiceRxjava apiServiceRxjava;


    public GetProgramModelRx(GetProgramMVP.RequiredPresenterOps mPresenter) {
        Log.i("RxJavaRequest", "GetProgramModelRx: "+ccTakhfifHajmis);
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
        serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
    }


    @Override
    public void getAllForoshandehMamorPakhsh() {
        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        final ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = foroshandehMamorPakhshDAO.getAll();
        //final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtil = new ForoshandehMamorPakhshUtils();
        for (int i = 0; i < foroshandehMamorPakhshModels.size(); i++) {
            int resId = foroshandehMamorPakhshUtil.getNoeForoshandehMamorPakhsh(foroshandehMamorPakhshModels.get(i));
            foroshandehMamorPakhshModels.get(i).setNameNoeForoshandehMamorPakhsh(mPresenter.getAppContext().getResources().getString(resId));
        }
        mPresenter.onGetAllForoshandehMamorPakhsh(foroshandehMamorPakhshModels);


    }

    @Override
    public void getProgram(int getProgramType, String date, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {

        ccMasirList = new ArrayList<>();
        this.foroshandehMamorPakhshModel = foroshandehMamorPakhshModel;
        ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
        ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccPosShomarehHesab = foroshandehMamorPakhshModel.getCcPosShomarehHesab();
        ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
        ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        ccMasirs = "-1";
        ccMoshtarys = "-1,";
        ccGorohs.set("347");
        ccGorohss = "347";
        ccDarkhastFaktors = "-1";
        ccDarkhastFaktorPakhsh = "-1";
        ccMoshtaryPakhsh = "-1";
        ccForoshandehString = "-1";
        anbarakAfrad = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        itemCounter = -1;
        this.date = date;
        selectedDateGregorian = "";
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        ccTakhfifHajmiList = new HashSet<>();
        ccTakhfifSenfiList = new HashSet<>();
        ccJayezehList = new HashSet<>();

        mPresenter.onGetNoeMasouliat(noeMasouliat);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessfullyGetNewProgramItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedGetProgram(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        try {
            String[] sepratedDate = date.split("/");
            PubFunc.DateConverter dateConverter = new PubFunc().new DateConverter();
            dateConverter.persianToGregorian(Integer.parseInt(sepratedDate[0]), Integer.parseInt(sepratedDate[1]), Integer.parseInt(sepratedDate[2]));
            String year = String.valueOf(dateConverter.getYear());
            String month = dateConverter.getMonth() > 9 ? String.valueOf(dateConverter.getMonth()) : "0" + dateConverter.getMonth();
            String day = dateConverter.getDay() > 9 ? String.valueOf(dateConverter.getDay()) : "0" + dateConverter.getDay();
            selectedDateGregorian = mPresenter.getAppContext().getResources().getString(R.string.dateWithSplashFormat, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (noeMasouliat != 7) {
            getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.getProgramItemsRx).length;
            deleteLogPPCAndAdamDarkhast(getProgramType, String.valueOf(ccForoshandeh));
        } else // if (noeMasouliat == 7) Amargar
        {
            getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.getProgramAmargarItems).length;
            deletePorseshnameh();
        }
    }


    @Override
    public void setProgramDateToShared() {
        GetProgramShared shared = new GetProgramShared(mPresenter.getAppContext());
        Log.d("getProgram", "date : " + date);
        Log.d("getProgram", "dateGregorian : " + selectedDateGregorian);
        shared.removeAll();
        shared.putString(shared.PERSIAN_DATE_OF_GET_PROGRAM(), date);
        shared.putString(shared.GREGORIAN_DATE_OF_GET_PROGRAM(), selectedDateGregorian);

    }

    @Override
    public void updateForoshandeh(final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        int ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();

        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        final String deviceIMEI = deviceInfo.getIMEI(mPresenter.getAppContext());

        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        //int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0); //0-Main , 1-Test
        String usingIMEI = userTypeShared.getString(userTypeShared.USING_IMEI(), deviceIMEI);

        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        foroshandehMamorPakhshDAO.fetchForoshandehMamorPakhshForUpdate(mPresenter.getAppContext(), GetProgramActivity.class.getSimpleName(), usingIMEI, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                            mPresenter.onSuccessUpdateForoshandeh();
                        } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                            mPresenter.onFailedUpdateForoshandeh(mPresenter.getAppContext().getResources().getString(R.string.errorUpdateForoshandeh));
                        }
                        return false;
                    }
                });

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = foroshandehMamorPakhshDAO.deleteAll();
                        boolean insertResult = foroshandehMamorPakhshDAO.insertGroup(arrayListData);

                        if (((ForoshandehMamorPakhshModel) arrayListData.get(0)).getNoeForoshandehMamorPakhsh() == 3) {
                            foroshandehMamorPakhshDAO.updateCanSetFaktorKharejAzMahal(((ForoshandehMamorPakhshModel) arrayListData.get(0)).getCcForoshandeh());
                        }

                        if (deleteResult && insertResult) {
                            Message message = new Message();
                            message.arg1 = Constants.BULK_INSERT_SUCCESSFUL();
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.arg1 = Constants.BULK_INSERT_FAILED();
                            handler.sendMessage(message);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                String message = type + "\n" + error;
                mPresenter.onFailedUpdateForoshandeh(message);
            }
        });

    }

    @Override
    public void updateKalaModatVosol(int getProgramType, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        ccGorohs.set("347");
        ccGorohss = "347";
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        anbarakAfrad = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne());
        itemCounter = 0;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateKalaModatVosol).length;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateKalaModatVosolItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateKalaModatVosolItem(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());

        ccGorohs.set(moshtaryDAO.getAllccNoeSenf());
        ccGorohss = moshtaryDAO.getAllccNoeSenf();
        Log.d("GetProgram", "ccGorohs before modat vosol: " + ccGorohs.get());
        getModatVosol(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs.get());
    }

    @Override
    public void updateJayezehTakhfif(int getProgramType, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = 0;
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne());
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateJayezehTakhfif).length;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateJayezehTakhfifItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateJayezehTakhfif(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getJayezeh(getProgramType);
    }

    @Override
    public void updateCustomers(int getProgramType, String date, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        this.foroshandehMamorPakhshModel = foroshandehMamorPakhshModel;
        ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = 0;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateCustomers).length;
        ccMoshtarys = "-1,";
        ccMasirs = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne());
        this.date = date;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateCustomers(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateCustomers(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        try {
            String[] sepratedDate = date.split("/");
            PubFunc.DateConverter dateConverter = new PubFunc().new DateConverter();
            dateConverter.persianToGregorian(Integer.parseInt(sepratedDate[0]), Integer.parseInt(sepratedDate[1]), Integer.parseInt(sepratedDate[2]));
            String year = String.valueOf(dateConverter.getYear());
            String month = dateConverter.getMonth() > 9 ? String.valueOf(dateConverter.getMonth()) : "0" + dateConverter.getMonth();
            String day = dateConverter.getDay() > 9 ? String.valueOf(dateConverter.getDay()) : "0" + dateConverter.getDay();
            selectedDateGregorian = mPresenter.getAppContext().getResources().getString(R.string.dateWithSplashFormat, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //differentPart
        getMasir(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMarkazForosh), selectedDateGregorian, selectedDateGregorian, "1");
    }


    @Override
    public void updateParameter(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = -1;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateParameters).length;
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateParameters(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateParameters(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getParameter(Constants.GET_PROGRAM_UPDATE_PARAMETERS());
    }

    @Override
    public void updateEtebarForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateEtebarForoshandeh).length;
        itemCounter = -1;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateEtebarForoshandeh(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateEtebarForoshandeh(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getEtebar(Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH());
    }





    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            if (!compositeDisposable.isDisposed()) {
                compositeDisposable.clear();
                compositeDisposable = null;
            }
        }


    }

    @Override
    public void clearRam() {
        listCodeMoshtary.clear();
        listccforoshandeh.clear();
    }

    @Override
    public void releaseResources() {
        if (compositeDisposable != null) {
            if (!compositeDisposable.isDisposed()) {
                compositeDisposable.clear();
                compositeDisposable = null;
            }
        }
    }

    @Override
    public void getProgramServiceType() {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        int service = systemConfigTabletDAO.getProgramService();
        mPresenter.onGetProgramServiceType(service);
    }


    ////////////////////////// GET PROGRAM //////////////////////////

    private void sendThreadMessage(int status, int itemIndex) {
        Message message = new Message();
        message.arg1 = status;
        message.arg2 = itemIndex;
        handler.sendMessage(message);
    }

    /**
     * start
     **/

    private void deleteLogPPCAndAdamDarkhast(final int getProgramType, String ccForoshandeh) {
        LogPPCRepository logPPCRepository = new LogPPCRepository(mPresenter.getAppContext());
        AdamDarkhastRepository adamDarkhastRepository = new AdamDarkhastRepository(mPresenter.getAppContext());
        Disposable disposableDelete = logPPCRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = adamDarkhastRepository.deleteAll()
                                .subscribe(deleteAll -> {
                                    if (deleteAll) {
                                        getFirstLevelApis(getProgramType);
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }


    private void getFirstLevelApis(final int getProgramType) {
        final int[] webCounter = {itemCounter};

        Log.i("webCounterLog", "getFirstLevelApis: " + webCounter[0]);
        Observable.zip(apiServiceRxjava.getAllBank().subscribeOn(Schedulers.io())
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getFirstLevelApis", "getAllBank"))
                        .doOnNext(getAllBankResultResponse -> webCounter[0]++)


                ,
                apiServiceRxjava.getAllBrand().subscribeOn(Schedulers.io())
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getFirstLevelApis", "getAllBrand"))
                        .doOnNext(getAllBrandResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getElatAdamDarkhast().subscribeOn(Schedulers.io())
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getFirstLevelApis", "getElatAdamDarkhast"))
                        .doOnNext(getAllvElatAdamDarkhastResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getElatMarjoeeKala().subscribeOn(Schedulers.io())
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getFirstLevelApis", "getElatMarjoeeKala"))
                        .doOnNext(getAllElatMarjoeeKalaResultResponse -> webCounter[0]++)

                ,

                (getAllBankResultResponse, getAllBrandResultResponse, getAllvElatAdamDarkhastResultResponse, getAllElatMarjoeeKalaResultResponse) -> {

                    Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "apply: ");


                    updateBankTable(getProgramType, getAllBankResultResponse.body().getData()
                            , getAllBrandResultResponse.body().getData()
                            , getAllvElatAdamDarkhastResultResponse.body().getData()
                            , getAllElatMarjoeeKalaResultResponse.body().getData());

                    Log.i("update Bank", "getFirstLevelApis: ");


                    return true;


                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                        Log.i("ApiTime", +itemCounter + "getFirstLevelApiys:onStart" + System.currentTimeMillis());
                    }

                    @Override
                    public void onNext(@NonNull Boolean getFirstLevelApis) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "getFirstLevelApis:");


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getCause()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getFirstLevelApis: " + webCounter[0]);

                        Log.i("ApiTime", +itemCounter + "getFirstLevelApis:onComplete" + System.currentTimeMillis());

                    }
                });

    }


    /**
     * Table1
     **/
    private void updateBankTable(int getProgramType
            , ArrayList<BankModel> bankModels
            , ArrayList<BrandModel> brandModels
            , ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels
            , ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {

        BankRepository bankRepository = new BankRepository(mPresenter.getAppContext());
        Disposable disposableDelete = bankRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = bankRepository.insertGroup(bankModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        updateBrandTable(getProgramType, brandModels, elatAdamDarkhastModels, elatMarjoeeKalaModels);
                                        Log.i("RxJavaRequest", "itemCounter " + itemCounter + "\t updateBankTable: ");

                                    } else {
                                        throwException("updateBankTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }

    /**
     * Table2
     **/
    private void updateBrandTable(int getProgramType
            , ArrayList<BrandModel> brandModels
            , ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels
            , ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {
        BrandRepository brandRepository = new BrandRepository(mPresenter.getAppContext());
        Disposable disposableDelete = brandRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = brandRepository.insertGroup(brandModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {

                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        updateElatAdamDarkhastTable(getProgramType, elatAdamDarkhastModels, elatMarjoeeKalaModels);
                                        Log.i("RxJavaRequest", "item index:" + itemCounter + "\t updateBrandTable ");

                                    } else {
                                        throwException("updateBrandTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * Table 3
     **/
    private void updateElatAdamDarkhastTable(int getProgramType, ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels, ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {
        ElatAdamDarkhastRepository elatAdamDarkhastRepository = new ElatAdamDarkhastRepository(mPresenter.getAppContext());
        Disposable disposableDelete = elatAdamDarkhastRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = elatAdamDarkhastRepository.insertGroup(elatAdamDarkhastModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        updateElatMarjoeeKalaTable(getProgramType, elatMarjoeeKalaModels);
                                        Log.i("RxJavaRequest", +itemCounter + "\t updateElatAdamDarkhastTable: ");

                                    } else {
                                        throwException("updateElatAdamDarkhastTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * table 4
     **/
    private void updateElatMarjoeeKalaTable(int getProgramType, ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels) {
        ElatMarjoeeKalaRepository elatMarjoeeKalaRepository = new ElatMarjoeeKalaRepository(mPresenter.getAppContext());
        Disposable disposableDelete = elatMarjoeeKalaRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = elatMarjoeeKalaRepository.insertGroup(elatMarjoeeKalaModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getLazyAllGoroh(getProgramType);
                                        Log.i("RxJavaRequest", +itemCounter + " \t updateElatMarjoeeKalaTable: ");
                                    } else {
                                        throwException("updateElatMarjoeeKalaTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);


    }


    /**
     * Api
     **/
    private void getLazyAllGoroh(int getProgramType) {
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getAllGoroh()
                .subscribeOn(Schedulers.io())
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getLazyAllGoroh", ""))
                .subscribe(new Observer<Response<GetAllGorohResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllGorohResult> getAllGorohResultResponse) {
                        updateGorohTable(getProgramType, getAllGorohResultResponse.body().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getCause()));

                    }

                    @Override
                    public void onComplete() {
                        ++webCounter[0];

                    }
                });


    }

    /**
     * section 7
     **/
    private void updateGorohTable(int getProgramType, ArrayList<GorohModel> gorohModels) {

        GorohRepository gorohRepository = new GorohRepository(mPresenter.getAppContext());
        Disposable disposableDelete = gorohRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = gorohRepository.insertGroup(gorohModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        deleteGPSDataMashin(getProgramType);
                                        Log.i("RxJavaRequest", +itemCounter + "\t updateGorohTable: ");
                                    } else {
                                        throwException("updateGorohTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }


    /**
     * section 8
     **/
    private void deleteGPSDataMashin(int getProgramType) {
        GPSDataMashinRepository gpsDataMashinRepository = new GPSDataMashinRepository(mPresenter.getAppContext());
        gpsDataMashinRepository.deleteAll()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean deleteGPSData) {

                        getGPSData(getProgramType);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * section 8
     **/
    private void getGPSData(final int getProgramType) {
        GPSDataPpcRepository gpsDataPpcRepository = new GPSDataPpcRepository(mPresenter.getAppContext());
        gpsDataPpcRepository.deleteSendedRecords().subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Boolean deleteSendedRecords) {

                if (deleteSendedRecords) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    Log.i("RxJavaRequest", +itemCounter + "\t getGPSData: ");
                    getSecondAmbush(getProgramType);
                } else {
                    throwException("getGPSData");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

            }

            @Override
            public void onComplete() {

            }
        });


    }

    /**
     * section 9
     **/

    private void getSecondAmbush(int getProgramtype) {
        final int[] webCounter = {0};

        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0]);
        Observable.zip(apiServiceRxjava.getAllMahalByccMarkazForosh(String.valueOf(ccMarkazSazmanForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllMahalByccMarkazForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllMahalByccMarkazForoshResultResponse -> webCounter[0]++)
                ,
                apiServiceRxjava.getAllvMarkaz(String.valueOf(ccMarkazForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllvMarkazForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMarkazResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getAllvMarkaz(String.valueOf(ccMarkazAnbar))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllvMarkazAnbar"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMarkazResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getAllvMarkazShomarehHesab(String.valueOf(ccMarkazAnbar))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllvMarkazShomarehHesab"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMarkazShomarehHesabResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getPosShomarehHesab(String.valueOf(ccPosShomarehHesab))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getPosShomarehHesab"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getPosShomarehHesabResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getAllMasir(String.valueOf(ccForoshandeh), String.valueOf(ccMarkazForosh), selectedDateGregorian, selectedDateGregorian, "1")
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllMasir"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(getAllMasirResultResponse -> webCounter[0]++)

                ,
                apiServiceRxjava.getAllvPolygonForoshSatrByForoshandeh(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getSecondAmbush", "getAllvPolygonForoshSatrByForoshandeh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvPolygonForoshSatrByForoshandehResultResponse -> webCounter[0]++)
                ,
                (getAllMahalByccMarkazForoshResultResponse, getAllvMarkazForosh, getAllvMarkazAnbar, getAllvMarkazShomarehHesabResultResponse, getPosShomarehHesabResultResponse, getAllMasirResultResponse, getAllvPolygonForoshSatrByForoshandehResultResponse) -> {


                    updateSecondAmbushTables(getProgramtype, getAllMasirResultResponse.body().getData()
                            , getAllMahalByccMarkazForoshResultResponse.body().getData()
                            , getAllvMarkazForosh.body().getData()
                            , getAllvMarkazAnbar.body().getData()
                            , getAllvMarkazShomarehHesabResultResponse.body().getData()
                            , getPosShomarehHesabResultResponse.body().getData()
                            , getAllvPolygonForoshSatrByForoshandehResultResponse.body().getData());


                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean fetchedAllData) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be ten");
                    }
                });


    }

    /**
     * section 10
     **/
    private void updateSecondAmbushTables(int getProgramType, ArrayList<MasirModel> masirModels
            , ArrayList<MahalModel> mahalModels
            , ArrayList<MarkazModel> markazForoshModels
            , ArrayList<MarkazModel> markazAnbarModels
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {

        updateccMasirs(getProgramType
                , masirModels
                , mahalModels
                , markazForoshModels
                , markazAnbarModels
                , markazShomarehHesabModels
                , posShomarehHesabModels
                , polygonForoshSatrModels);
    }

    /**
     * section 11
     **/
    private void updateccMasirs(int getProgramType, ArrayList<MasirModel> masirModels
            , ArrayList<MahalModel> mahalModels
            , ArrayList<MarkazModel> markazForoshModels
            , ArrayList<MarkazModel> markazAnbarModels
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels
    ) {

        Observable.fromIterable(masirModels)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MasirModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MasirModel masirModel) {
                        ccMasirs += "," + masirModel.getCcMasir();
                        ccMasirList.add(String.valueOf(masirModel.getCcMasir()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        updateAllMasirTable(getProgramType
                                , masirModels
                                , mahalModels
                                , markazForoshModels
                                , markazAnbarModels
                                , markazShomarehHesabModels
                                , posShomarehHesabModels
                                , polygonForoshSatrModels);
                    }
                });

    }

    /**
     * section 12
     **/
    private void updateAllMasirTable(int getProgramType
            , ArrayList<MasirModel> masirModels
            , ArrayList<MahalModel> mahalModels
            , ArrayList<MarkazModel> markazForoshModels
            , ArrayList<MarkazModel> markazAnbarModel
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {
        MasirRepository masirRepository = new MasirRepository(mPresenter.getAppContext());


        Disposable disposableDelete = masirRepository.deleteAll()

                .subscribe(deleteTable -> {
                    if (deleteTable) {

                        Disposable disposableInsert = masirRepository.insertGroup(masirModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {


                                        Disposable disposableUpdateDate = masirRepository.updateTarikhMasir(date)
                                                .subscribe(updateDate -> {
                                                    if (updateDate) {


                                                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);

                                                        } else {

                                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                                            Log.i("RxJavaRequest", +itemCounter + "updateAllMasirTable: ");
                                                            updateMahalTable(getProgramType
                                                                    , mahalModels
                                                                    , markazForoshModels
                                                                    , markazAnbarModel
                                                                    , markazShomarehHesabModels
                                                                    , posShomarehHesabModels
                                                                    , polygonForoshSatrModels);
                                                        }
                                                    } else {
                                                        throwException("updateAllMasirTable");
                                                    }
                                                }, throwable -> {
                                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                                    Log.i("RxJavaRequest", +itemCounter + "updateAllMasirTable: " + throwable.getMessage());

                                                });
                                        compositeDisposable.add(disposableUpdateDate);

                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * section 13
     **/
    private void updateMahalTable(int getProgramType, ArrayList<MahalModel> mahalModels
            , ArrayList<MarkazModel> markazForoshModel
            , ArrayList<MarkazModel> markazAnbarModel
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels
    ) {
        MahalRepository mahalRepository = new MahalRepository(mPresenter.getAppContext());
        Disposable disposableDelete = mahalRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = mahalRepository.insertGroup(mahalModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateMahalTable: " + insertGroup);

                                        updateMarkazForoshTable(getProgramType
                                                , markazForoshModel
                                                , markazAnbarModel
                                                , markazShomarehHesabModels
                                                , posShomarehHesabModels
                                                , polygonForoshSatrModels);
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);


    }

    /**
     * section 14
     **/
    private void updateMarkazForoshTable(int getProgramType
            , ArrayList<MarkazModel> markazForoshModels
            , ArrayList<MarkazModel> markazAnbarModels
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels
    ) {
        MarkazRepository markazRepository = new MarkazRepository(mPresenter.getAppContext());
        Disposable disposableDelete = markazRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = markazRepository.insertGroup(markazForoshModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "updateMarkazForoshTable: ");
                                        updateMarkazAnbarTable(getProgramType
                                                , markazAnbarModels
                                                , markazShomarehHesabModels
                                                , posShomarehHesabModels
                                                , polygonForoshSatrModels);

                                    } else {
                                        throwException("updateMarkazForoshTable");
                                    }
                                }, throwable ->
                                {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }

    /**
     * section 15
     **/

    private void updateMarkazAnbarTable(int getProgramType
            , ArrayList<MarkazModel> markazAnbarModels
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels
    ) {

        MarkazRepository markazRepository = new MarkazRepository(mPresenter.getAppContext());
        markazRepository.insertGroup(markazAnbarModels)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean insertMarkazAnbar) {
                        Log.i("RxJavaRequest", +itemCounter + "updateMarkazAnbarTable " + insertMarkazAnbar);
                        if (insertMarkazAnbar) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            updateMarkazShomarehHesabTable(getProgramType
                                    , markazShomarehHesabModels
                                    , posShomarehHesabModels
                                    , polygonForoshSatrModels);
                        } else {
                            throwException("updateMarkazAnbarTable");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * section 16
     **/

    private void updateMarkazShomarehHesabTable(int getProgramType
            , ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels
            , ArrayList<PosShomarehHesabModel> posShomarehHesabModels
            , ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels
    ) {
        MarkazShomarehHesabRepository markazShomarehHesabRepository = new MarkazShomarehHesabRepository(mPresenter.getAppContext());
        Disposable disposableDelete = markazShomarehHesabRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = markazShomarehHesabRepository.insertGroup(markazShomarehHesabModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "updateMarkazShomarehHesabTable: ");
                                        updatePosShomarehHesabTable(getProgramType, posShomarehHesabModels, polygonForoshSatrModels);
                                    } else {
                                        throwException("updateMarkazShomarehHesabTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * section 17
     **/

    private void updatePosShomarehHesabTable(int getProgramType,
                                             ArrayList<PosShomarehHesabModel> posShomarehHesabModels,
                                             ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {
        PosShomarehHesabRepository posShomarehHesabRepository = new PosShomarehHesabRepository(mPresenter.getAppContext());
        Disposable disposableDelete = posShomarehHesabRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = posShomarehHesabRepository.insertGroup(posShomarehHesabModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {

                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updatePosShomarehHesabTable: ");
                                        updateForoshandehPolygonTable(getProgramType, polygonForoshSatrModels);
                                    } else {
                                        throwException("updatePosShomarehHesabTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }


    /**
     * section 18
     **/
    private void updateForoshandehPolygonTable(int getProgramType, ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels) {
        PolygonForoshSatrRepository polygonForoshSatrRepository = new PolygonForoshSatrRepository(mPresenter.getAppContext());
        Disposable disposableDelete = polygonForoshSatrRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = polygonForoshSatrRepository.insertGroup(polygonForoshSatrModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateForoshandehPolygonTable:");
                                        getMasirVaznHajmRx(getProgramType);
                                    } else {
                                        throwException("updateForoshandehPolygonTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    Log.i("RxJavaRequest", +itemCounter + "updateForoshandehPolygonTable: " + throwable.getMessage());
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }

    /**
     * section 20
     **/

    private void getMasirVaznHajmRx(int getProgramType) {
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getMasirVaznHajmMashin("-1," + ccMasirs)
                .subscribeOn(Schedulers.io())
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMasirVaznHajmRx", ""))
                .subscribe(new Observer<Response<GetMairVaznHajmMashinResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetMairVaznHajmMashinResult> getMairVaznHajmMashinResultResponse) {
                        webCounter[0]++;
                        if (getMairVaznHajmMashinResultResponse.isSuccessful()) {
                            updateMasirVaznHajmMashinTable(getProgramType, getMairVaznHajmMashinResultResponse.body().getData());
                        } else {
                            throwException("getMasirVaznHajmRx");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be eleven");
                    }
                });
    }

    /**
     * section 21
     **/

    private void updateMasirVaznHajmMashinTable(int getProgramType, ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels) {
        MasirVaznHajmMashinRepository masirVaznHajmMashinRepository = new MasirVaznHajmMashinRepository(mPresenter.getAppContext());
        Disposable disposableDelete = masirVaznHajmMashinRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = masirVaznHajmMashinRepository.insertGroup(masirVaznHajmMashinModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMasirVaznHajmMashinTable: ");
                                        deleteMojoodiGiri(getProgramType, ccMasirs);

                                    } else {
                                        throwException("updateMasirVaznHajmMashinTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }


    /**
     * section 22
     **/

    private void deleteMojoodiGiri(final int getProgramType, String ccMasirs) {
        deleteMojodiGiriRx(getProgramType);
    }


    /**
     * section 23
     **/

    private void deleteMojodiGiriRx(int getProgramType) {
        MojoodiGiriRepository mojoodiGiriRepository = new MojoodiGiriRepository(mPresenter.getAppContext());
        mojoodiGiriRepository.deleteAll()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean deleteMojodiGiri) {
                        if (deleteMojodiGiri) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            Log.d("RxJavaRequest", "itemCounter:" + itemCounter + "\t deleteMojodiGiriRx ");
                            getMoshtaryRx(getProgramType);

                        } else {
                            throwException("deleteMojodiGiriRx");
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * section 21
     **/

    private void getMoshtaryRx(int getProgramType) {
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getAllMoshtaryByccMasir(String.valueOf(ccForoshandeh), ccMasirs, "-1")
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "", "getMoshtaryRx"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetAllMoshtaryByccMasirResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllMoshtaryByccMasirResult> getAllMoshtaryByccMasirResultResponse) {
                        webCounter[0]++;
//                        Log.i("getMoshtaryRx", "onNext: " + getAllMoshtaryByccMasirResultResponse.body().getData().get(0).toString());
                        getCcMoshtaries(getProgramType, getAllMoshtaryByccMasirResultResponse.body().getData());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "cause: " + e.getCause() + "message:" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be twelve");
                    }
                });
    }

    private void getCcMoshtaries(int getProgramType, ArrayList<MoshtaryModel> moshtaryModels) {
        Observable.fromIterable(moshtaryModels)
                .subscribe(new Observer<MoshtaryModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull MoshtaryModel moshtaryModel) {
                if(!ccMoshtarys.contains(String.valueOf(moshtaryModel.getCcMoshtary())))
                ccMoshtarys += moshtaryModel.getCcMoshtary() + ",";

            }

            @Override
            public void onError(@NonNull Throwable e) {
                sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
            }

            @Override
            public void onComplete() {

                if (ccMoshtarys.length() != 0) {
                    ccMoshtarys = ccMoshtarys.substring(0, ccMoshtarys.length() - 1);
                }

                updateMoshtaryTable(getProgramType, moshtaryModels);
            }
        });
    }


    /**
     * section 22
     **/

    private void updateMoshtaryTable(int getProgramType, ArrayList<MoshtaryModel> moshtaryModels) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());
        Disposable disposableDelete = moshtaryRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = moshtaryRepository.insertGroup(moshtaryModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + " updateMoshtaryTable");
                                        fetchMoshtaryApiAmbush(getProgramType);
                                    } else {
                                        throwException("updateMoshtaryTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * section 23
     **/
    private void fetchMoshtaryApiAmbush(int getProgramType) {
        final int[] webCounter = {itemCounter};

        Observable.zip(apiServiceRxjava.getMoshtaryFirstParentPPC(ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchMoshtaryApiAmbush", "getMoshtaryFirstParentPPC"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMoshtarysFirstParentPPCResultResponse -> webCounter[0]++)
                ,
                apiServiceRxjava.getMoshtaryAddressByNoeMasouliat(String.valueOf(ccForoshandeh), ccMasirs, "-1")
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchMoshtaryApiAmbush", "getMoshtaryAddressByNoeMasouliat"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryAddressResultResponse -> webCounter[0]++)
                ,
                apiServiceRxjava.getAllvMoshtaryAfrad(ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchMoshtaryApiAmbush", "getAllvMoshtaryAfrad"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryAfradResultResponse -> webCounter[0]++)
                ,

                apiServiceRxjava.getAllvMoshtaryEtebarSazmanForosh(ccMoshtarys, String.valueOf(ccSazmanForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchMoshtaryApiAmbush", "getAllvMoshtaryEtebarSazmanForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryEtebarSazmanForoshResultResponse -> webCounter[0]++)
                , (getMoshtarysFirstParentPPCResultResponse, getAllvMoshtaryAddressResultResponse, getAllvMoshtaryAfradResultResponse, getAllvMoshtaryEtebarSazmanForoshResultResponse) -> {

                    updateMoshtaryTableWithParent(getProgramType, getMoshtarysFirstParentPPCResultResponse.body().getData(), getAllvMoshtaryAddressResultResponse.body().getData(), getAllvMoshtaryAfradResultResponse.body().getData(), getAllvMoshtaryEtebarSazmanForoshResultResponse.body().getData());

                    return true;
                }


        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean fetchMoshtaryApiAmbush) {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", "onError: " + e.getMessage());
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be sixtheen");
                    }
                });
    }


    /**
     * section 24
     **/

    private void updateMoshtaryTableWithParent(int getProgramType
            , ArrayList<MoshtaryParentModel> moshtaryParentModels
            , ArrayList<MoshtaryAddressModel> moshtaryAddressModels
            , ArrayList<MoshtaryAfradModel> moshtaryAfradModels
            , ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());

        moshtaryRepository.updateccMoshtaryParentInMoshtary(moshtaryParentModels).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean updateMoshtaryTableWithParent) {

                        if (updateMoshtaryTableWithParent) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            Log.i("RxJavaRequest", "itemCounter " + itemCounter + "\t updateMoshtaryTableWithParent");
                            updateMoshtaryAddressTable(getProgramType
                                    , moshtaryAddressModels
                                    , moshtaryAfradModels
                                    , moshtaryEtebarSazmanForoshModels);


                        } else {
                            throwException("updateMoshtaryTableWithParent");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * section 25
     **/

    private void updateMoshtaryAddressTable(int getProgramType, ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<MoshtaryAfradModel> moshtaryAfradModels, ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        MoshtaryAddressRepository moshtaryAddressRepository = new MoshtaryAddressRepository(mPresenter.getAppContext());
        Disposable disposableDelete = moshtaryAddressRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = moshtaryAddressRepository.insertGroup(moshtaryAddressModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateMoshtaryAddressTable");
                                        updateMoshtaryAfradTable(getProgramType
                                                , moshtaryAfradModels
                                                , moshtaryEtebarSazmanForoshModels);
                                    } else {
                                        throwException("updateMoshtaryAddressTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);


    }

    /**
     * section 26
     **/

    private void updateMoshtaryAfradTable(int getProgramType
            , ArrayList<MoshtaryAfradModel> moshtaryAfradModels
            , ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        MoshtaryAfradRepository moshtaryAfradRepository = new MoshtaryAfradRepository(mPresenter.getAppContext());
        Disposable disposableDelete = moshtaryAfradRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = moshtaryAfradRepository.insertGroup(moshtaryAfradModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMoshtaryAfradTable");
                                        updateMoshtaryEtebarSazmanForoshTable(getProgramType
                                                , moshtaryEtebarSazmanForoshModels);
                                    } else {
                                        throwException("updateMoshtaryAfradTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);

    }

    /**
     * section 27
     **/

    private void updateMoshtaryEtebarSazmanForoshTable(int getProgramType, ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels) {
        MoshtaryEtebarSazmanForoshRepository moshtaryEtebarSazmanForoshRepository = new MoshtaryEtebarSazmanForoshRepository(mPresenter.getAppContext());
        Disposable disposableDelete = moshtaryEtebarSazmanForoshRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = moshtaryEtebarSazmanForoshRepository.insertGroup(moshtaryEtebarSazmanForoshModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateMoshtaryEtebatSazmanForoshTable");
                                        getMoshtaryGorohRx(getProgramType);
                                    } else {
                                        throwException("updateMoshtaryEtebatSazmanForoshTable");
                                    }
                                }, throwable -> {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });

                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDelete);


    }

    /**
     * section 28
     **/

    private void getMoshtaryGorohRx(int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter " + itemCounter + "\t MoshtaryGorohRx");

        if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
//            getBargashty(getProgramType);
        } else {
            fetchSecondMoshtaryApiAmbush(getProgramType);
        }
    }

    /**
     * section 29
     **/

    private void fetchSecondMoshtaryApiAmbush(int getProgramType) {
        final GetMoshtaryPolygonResult[] moshtaryPolygonResult = new GetMoshtaryPolygonResult[1];
        final GetAllvMoshtaryShomarehHesabResult[] getAllvMoshtaryShomarehHesabResult = new GetAllvMoshtaryShomarehHesabResult[1];

        final int[] webCounter = {itemCounter};

        Observable.zip(apiServiceRxjava.getMoshtaryPolygon(ccMasirs, ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchSecondMoshtaryApiAmbush", "getMoshtaryPolygon"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMoshtaryPolygonResultResponse -> webCounter[0]++)
                ,
                apiServiceRxjava.getAllvMoshtaryShomarehHesab(ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchSecondMoshtaryApiAmbush", "getAllvMoshtaryShomarehHesab"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryShomarehHesabResultResponse -> webCounter[0]++)
                ,
                (getMoshtaryPolygonResultResponse, getAllvMoshtaryShomarehHesabResultResponse) -> {
                    moshtaryPolygonResult[0] = getMoshtaryPolygonResultResponse.body();
                    getAllvMoshtaryShomarehHesabResult[0] = getAllvMoshtaryShomarehHesabResultResponse.body();


                    return true;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean fetchSecondMoshtaryApiAmbush) {
                        updateMoshtaryPolygonTable(getProgramType, moshtaryPolygonResult[0], getAllvMoshtaryShomarehHesabResult[0]);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be eighteen");
                    }
                });
    }

    /**
     * section 30
     **/

    private void updateMoshtaryPolygonTable(int getProgramType, GetMoshtaryPolygonResult moshtaryPolygonModels, GetAllvMoshtaryShomarehHesabResult moshtaryShomarehHesabModels) {
        if (moshtaryPolygonModels != null) {
            MoshtaryPolygonRepository moshtaryPolygonRepository = new MoshtaryPolygonRepository(mPresenter.getAppContext());
            Disposable disposableDelete = moshtaryPolygonRepository.deleteAll()
                    .subscribe(deleteTable -> {
                        if (deleteTable) {
                            Disposable disposableInsert = moshtaryPolygonRepository.insertGroup(moshtaryPolygonModels.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("moshtaryPolygonModels", "itemCounter:" + itemCounter + "\n updateMoshtaryPolygonTable: ");
                                            updateMoshtaryShomarehHesabTable(getProgramType, moshtaryShomarehHesabModels);

                                        } else {
                                            throwException("updateMoshtaryPolygonTable");
                                        }
                                    }, throwable -> {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    });

                            compositeDisposable.add(disposableInsert);
                        }
                    }, throwable -> {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    });
            compositeDisposable.add(disposableDelete);

        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("moshtaryPolygonModels", "itemCounter:" + itemCounter + "\n updateMoshtaryPolygonTable: ");
            updateMoshtaryShomarehHesabTable(getProgramType, moshtaryShomarehHesabModels);
        }
    }

    /**
     * section 31
     **/

    private void updateMoshtaryShomarehHesabTable(int getProgramType, GetAllvMoshtaryShomarehHesabResult moshtaryShomarehHesabResult) {
        if (moshtaryShomarehHesabResult != null) {
            MoshtaryShomarehHesabRepository moshtaryShomarehHesabRepository = new MoshtaryShomarehHesabRepository(mPresenter.getAppContext());
            Disposable disposableDelete = moshtaryShomarehHesabRepository.deleteAll()
                    .subscribe(deleteTable -> {
                        if (deleteTable) {
                            Disposable disposableInsert = moshtaryShomarehHesabRepository.insertGroup(moshtaryShomarehHesabResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateMoshtaryShomarehHesabTable: ");
                                            deleteMoshtaryTaghiratRx(getProgramType);
                                        } else {
                                            throwException("updateMoshtaryShomarehHesabTable");
                                        }
                                    }, throwable -> {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    });

                            compositeDisposable.add(disposableInsert);
                        }
                    }, throwable -> {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    });
            compositeDisposable.add(disposableDelete);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateMoshtaryShomarehHesabTable: ");
            deleteMoshtaryTaghiratRx(getProgramType);
        }


    }

    /**
     * section 32
     **/

    private void deleteMoshtaryTaghiratRx(int getProgramType) {
        MoshtaryTaghiratRepository moshtaryTaghiratRepository = new MoshtaryTaghiratRepository(mPresenter.getAppContext());
        moshtaryTaghiratRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean deleteMoshtaryTaghiratRx) {
                        if (deleteMoshtaryTaghiratRx) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t deleteMoshtaryTaghiratRx");
                            getDarkhastFaktorRx(getProgramType);
                        } else {
                            throwException("deleteMoshtaryTaghiratRx");
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * section 33
     **/
    private void getDarkhastFaktorRx(int getProgramType) {
        final int[] webCounter = {itemCounter};

        apiServiceRxjava.getDarkhastFaktor(String.valueOf(ccAfrad), ccMoshtarys)
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDarkhastFaktorRx", ""))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetDarkhastFaktorResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetDarkhastFaktorResult> getDarkhastFaktorResultResponse) {
                        //TODO
                        webCounter[0]++;
                        updateFaktorDetails(getProgramType, getDarkhastFaktorResultResponse.body().getData());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "onError: " + e.getMessage());

                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be nintheen");
                    }
                });


    }

//    int darkhastFaktorEmzaMoshtaryCounter = 0;


    /**
     * section 34
     **/
    private void updateFaktorDetails(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
        final long[] ccDarkhastFaktorHavaleh = new long[1];
        final int[] ccMoshtary = new int[1];
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = new ArrayList<>();

        Observable.fromIterable(darkhastFaktorModels)
                .filter(darkhastFaktorModel -> darkhastFaktorModel.getFaktorRooz()==0)
                .flatMap(darkhastFaktorModel -> {
                    ccDarkhastFaktors += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                    if (darkhastFaktorModel.getForTasviehVosol() == 1) {
                        ccDarkhastFaktorPakhsh += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                        ccForoshandehString += "," + darkhastFaktorModel.getCcForoshandeh();
                    }

                    ccDarkhastFaktorHavaleh[0] = darkhastFaktorModel.getCcDarkhastFaktor();
                    ccMoshtary[0] = darkhastFaktorModel.getCcMoshtary();
                    if (darkhastFaktorModel.getCodeVazeiat() != Constants.CODE_VAZEIAT_FAKTOR_TASVIEH) {
                        String ccDarkhastFaktor = "0";
                        String ccDarkhastHavaleh = "0";
                        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3) {
                            ccDarkhastFaktor = String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor());
                        } else if (noeMasouliat == 4 || noeMasouliat == 5) {
                            ccDarkhastHavaleh = String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor());
                        }


                        return apiServiceRxjava.getImageJSON(ccDarkhastFaktor, ccDarkhastHavaleh)
                                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getImageJSON", ""))
                                .map(getImageJsonResultResponse -> {


                                    if (getImageJsonResultResponse.body().getData() != null) {
                                        if (getImageJsonResultResponse.body().getData().size() > 0) {
                                            GetImageStringModel getImageStringModel = getImageJsonResultResponse.body().getData().get(0);
                                            DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();
                                            darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(ccDarkhastFaktorHavaleh[0]);
                                            darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(ccMoshtary[0]);
                                            darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(Base64.decode(getImageStringModel.getImage(), Base64.NO_WRAP));
                                            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(1);
                                            return darkhastFaktorEmzaMoshtaryModel;
                                        } else {
                                            return null;

                                        }

                                    } else {
                                        return null;
                                    }
                                });

                    }
                    return null;
                })


                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DarkhastFaktorEmzaMoshtaryModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel) {
                        Log.i("DarkhastFaktorEmza", "ccDarkhastFaktorHavaleh " + ccDarkhastFaktorHavaleh[0] + "ccMoshtary" + ccMoshtary[0]);
                        if (darkhastFaktorEmzaMoshtaryModel != null)
                            darkhastFaktorEmzaMoshtaryModels.add(darkhastFaktorEmzaMoshtaryModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "onError:updateFaktorDetails " + e.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {

                        updateDarkhastFaktorEmzaMoshtaryTable(getProgramType, darkhastFaktorModels, darkhastFaktorEmzaMoshtaryModels);
//                        Log.i("RxJavaRequest", "onNext: darkhastFaktorEmzaMoshtaryRepository");


                    }

                });

    }

    private void updateDarkhastFaktorEmzaMoshtaryTable(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels, ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels) {

        DarkhastFaktorEmzaMoshtaryRepository darkhastFaktorEmzaMoshtaryRepository = new DarkhastFaktorEmzaMoshtaryRepository(mPresenter.getAppContext());
        Disposable disposable = darkhastFaktorEmzaMoshtaryRepository.deleteAll().subscribe(deleteAll -> {
            Disposable disposableInsert = darkhastFaktorEmzaMoshtaryRepository.insertGroup(darkhastFaktorEmzaMoshtaryModels)
                    .subscribe(insert -> {
                        if (insert) {
                            updateDarkhastFaktorTable(getProgramType, darkhastFaktorModels);
                        } else {
                            throwException("updateFaktorDetails");
                        }
                        Log.i("RxJavaRequest", "onNext: darkhastFaktorEmzaMoshtaryRepository");
                    }, throwable -> {
                        throwException("updateFaktorDetails");

                    });
            compositeDisposable.add(disposableInsert);
        });
        compositeDisposable.add(disposable);
    }


    /**
     * section 35
     **/
    private void updateDarkhastFaktorTable(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        Disposable disposableDelete = darkhastFaktorRepository.deleteAll()
                .subscribe(deleteTable -> {
                    if (deleteTable) {
                        Disposable disposableInsert = darkhastFaktorRepository.insertGroupFromGetProgram(darkhastFaktorModels, noeMasouliat)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {

                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateDarkhastFaktorTable");
                                        updateMoshtaryTableByDarkhastFaktorDetails(getProgramType, darkhastFaktorModels);

                                    } else {
                                        throwException("updateDarkhastFaktorTable");
                                    }
                                }, throwable -> {
                                    throwException("updateDarkhastFaktorTable");
                                });
                        compositeDisposable.add(disposableInsert);
                    }
                }, throwable -> {
                    throwException("updateDarkhastFaktorTable");
                });

        compositeDisposable.add(disposableDelete);


    }

    /**
     * section 36
     **/
    private void updateMoshtaryTableByDarkhastFaktorDetails(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {

        Observable.fromIterable(darkhastFaktorModels)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DarkhastFaktorModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull DarkhastFaktorModel darkhastFaktorModel) {
                        updateSazmanMoshtarian(darkhastFaktorModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "onError: updateMoshtaryTableByDarkhastFaktorDetails" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.i("RxJavaRequest", "onComplete: updateSazmanMoshtarian" + itemCounter);
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getDarkhastFaktorSatrRx(getProgramType);

                    }
                });

    }

    /**
     * section 37
     **/
    private void updateSazmanMoshtarian(DarkhastFaktorModel darkhastFaktorModel) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());

        moshtaryRepository.getByccMoshtary(darkhastFaktorModel.getCcMoshtary())
                .subscribe(new Observer<MoshtaryModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull MoshtaryModel moshtaryModel) {
                        if (moshtaryModel.getCcMoshtary() == 0) {
                            ccMoshtaryPakhsh += "," + darkhastFaktorModel.getCcMoshtary();
                            ccSazmanForoshPakhsh += "," + darkhastFaktorModel.getCcSazmanForosh();
                            if (!ccMarkazForoshPakhsh.contains(String.valueOf(darkhastFaktorModel.getCcMarkazForosh()))&&darkhastFaktorModel.getCcDarkhastFaktorNoeForosh() == 2)
                            {
                                ccMarkazForoshPakhsh += "," + darkhastFaktorModel.getCcMarkazForosh();
                            }
                            if (!ccMarkazSazmanForoshPakhsh.contains(String.valueOf(darkhastFaktorModel.getCcMarkazSazmanForosh())) && darkhastFaktorModel.getCcDarkhastFaktorNoeForosh() == 2)
                            {
                                ccMarkazSazmanForoshPakhsh += "," + darkhastFaktorModel.getCcMarkazSazmanForosh();
                            }
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            Log.i("RxJavaRequest", +itemCounter + "updateSazmanMoshtarian:");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        Log.i("RxJavaRequest", "onError: updateSazmanMoshtarian" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }


    private void getImageDarkhastFaktor(ImageDarkhastFaktorDAO imageDarkhastFaktorDAO, final DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO, int codeVazeiatFaktor, final long ccDarkhastFaktorHavaleh, final int ccMoshtary) {
        if (codeVazeiatFaktor != Constants.CODE_VAZEIAT_FAKTOR_TASVIEH) {
            String ccDarkhastFaktor = "0";
            String ccDarkhastHavaleh = "0";
            if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3) {
                ccDarkhastFaktor = String.valueOf(ccDarkhastFaktorHavaleh);
            } else if (noeMasouliat == 4 || noeMasouliat == 5) {
                ccDarkhastHavaleh = String.valueOf(ccDarkhastFaktorHavaleh);
            }
            Log.d("getProgram", "getImageDarkhastFaktor ccDarkhastFaktors : " + ccDarkhastFaktor + " , ccDarkhastHavaleh:" + ccDarkhastHavaleh);
            imageDarkhastFaktorDAO.fetchGetImageJSON(mPresenter.getAppContext(), "GetProgramActivity", ccDarkhastFaktor, ccDarkhastHavaleh, new RetrofitResponse() {
                @Override
                public void onSuccess(ArrayList arrayListData) {
                    if (arrayListData != null && arrayListData.size() > 0) {
                        try {
                            DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();
                            darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(ccDarkhastFaktorHavaleh);
                            darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(ccMoshtary);
                            darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(Base64.decode(((GetImageStringModel) arrayListData.get(0)).getImage(), Base64.NO_WRAP));
                            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(1);
                            darkhastFaktorEmzaMoshtaryDAO.insert(darkhastFaktorEmzaMoshtaryModel);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "GetProgramModel", "", "getDarkhastFaktor", "imageDarkhastFaktorDAO.fetchImageStringForMamorPakhsh.onResponse");
                        }
                    }
                }

                @Override
                public void onFailed(String type, String error) {
                    setLogToDB(Constants.LOG_EXCEPTION(), type + "\n" + error, "GetProgramModel", "", "getDarkhastFaktor", "imageDarkhastFaktorDAO.fetchImageStringForMamorPakhsh.onFailed");
                }
            });
        }
    }

    /**
     * section 38
     **/
    private void getDarkhastFaktorSatrRx(int getProgramType) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        final Map<Integer, Boolean> mapResult = new HashMap<>();
        Disposable getccDarkhastFaktorsByNoeFaktorHavaleRooz = darkhastFaktorRepository.getccDarkhastFaktorsByNoeFaktorHavaleRooz(Constants.ccNoeFaktor)
                .subscribe(s -> {
                    String ccDarkhastFaktorNoeFaktor = s;
                    Disposable getccDarkhastFaktorsByNoeFaktorHavale = darkhastFaktorRepository.getccDarkhastFaktorsByNoeFaktorHavale(Constants.ccNoeHavale)
                            .subscribe(s1 -> {
                                String ccDarkhastFaktorNoeHavale = s1;

                                mapResult.put(Constants.ccNoeFaktor, true);
                                mapResult.put(Constants.ccNoeHavale, true);
                                DarkhastFaktorSatrRepository darkhastFaktorSatrRepository = new DarkhastFaktorSatrRepository(mPresenter.getAppContext());
                                Disposable deleteAll = darkhastFaktorSatrRepository.deleteAll().subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean deleteAll) {
                                        if (!ccDarkhastFaktorNoeFaktor.trim().equals("")) {
                                            getDarkhastFaktorSatrNoe(deleteAll, Constants.ccNoeFaktor, ccDarkhastFaktorNoeFaktor, mapResult);

                                        }
                                        if (!ccDarkhastFaktorNoeHavale.trim().equals("")) {
                                            getDarkhastFaktorSatrNoe(deleteAll, Constants.ccNoeHavale, ccDarkhastFaktorNoeHavale, mapResult);
                                        }
                                        if (mapResult.get(Constants.ccNoeFaktor) && mapResult.get(Constants.ccNoeHavale)) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "getDarkhastFaktorSatr itemCounter" + itemCounter);
                                            getForoshandehsPakhshRx(getProgramType);

                                        } else {
                                            throwException("getDarkhastFaktorSatrRx");
                                        }

                                    }

                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "accept: ");
                                    throwException("getDarkhastFaktorSatrRx");
                                });
                                compositeDisposable.add(deleteAll);
                            }, throwable -> {
                                Log.i("RxJavaRequest", +itemCounter + "accept: ");
                                throwException("getDarkhastFaktorSatrRx");
                            });
                    compositeDisposable.add(getccDarkhastFaktorsByNoeFaktorHavale);
                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "accept: ");
                    throwException("getDarkhastFaktorSatrRx");
                });
        compositeDisposable.add(getccDarkhastFaktorsByNoeFaktorHavaleRooz);

    }

    private void getDarkhastFaktorSatrNoe(boolean deleteAll, int ccNoe, String ccDarkhastFaktorNoe, final Map<Integer, Boolean> mapResult) {
        final int[] webCounter = {itemCounter};

        apiServiceRxjava.getDarkhastFaktorSatr(String.valueOf(ccNoe), ccDarkhastFaktorNoe)
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDarkhastFaktorSatrNoe", ""))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetDarkhastFaktorSatrResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetDarkhastFaktorSatrResult> getDarkhastFaktorSatrResultResponse) {

                        if (getDarkhastFaktorSatrResultResponse.isSuccessful()) {
//                            darkhastFaktorSatrModels.addAll(getDarkhastFaktorSatrResultResponse.body().getData());
                            updateDarkhastFaktorSatrTable(deleteAll, Constants.ccNoeHavale, mapResult, getDarkhastFaktorSatrResultResponse.body().getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void updateDarkhastFaktorSatrTable(boolean deleteAll, int Noe, final Map<Integer, Boolean> mapResult, ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels) {
        DarkhastFaktorSatrRepository darkhastFaktorSatrRepository = new DarkhastFaktorSatrRepository(mPresenter.getAppContext());
        darkhastFaktorSatrRepository.insertGroup(darkhastFaktorSatrModels)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean insertGroup) {
                        if (Noe == Constants.ccNoeFaktor) {
                            if (insertGroup && deleteAll) {
                                mapResult.put(Constants.ccNoeFaktor, true);
                            } else {
                                mapResult.put(Constants.ccNoeFaktor, false);
                            }


                        }
                        if (Noe == Constants.ccNoeHavale) {
                            if (insertGroup && deleteAll) {
                                mapResult.put(Constants.ccNoeHavale, true);
                            } else {
                                mapResult.put(Constants.ccNoeHavale, false);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("rxrev", "onError: " + e.getCause().getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


//    private void getDarkhastFaktorSatr(final int getProgramType, String ccDarkhastFaktors) {
//        Log.d("getProgram", "ccDarkhastFaktors : " + ccDarkhastFaktors);
//        final DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
//        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
//        String ccDarkhastFaktorNoeFaktor = darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavaleRooz(DarkhastFaktorModel.ccNoeFaktor);
//        String ccDarkhastFaktorNoeHavale = darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavale(DarkhastFaktorModel.ccNoeHavale);
//        final Map<Integer, Boolean> mapResult = new HashMap<>();
//        mapResult.put(DarkhastFaktorModel.ccNoeFaktor, true);
//        mapResult.put(DarkhastFaktorModel.ccNoeHavale, true);
//        final boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
//        Log.d("getProgram", "ccDarkhastFaktorNoeFaktor:" + ccDarkhastFaktorNoeFaktor + " , ccDarkhastFaktorNoeHavale:" + ccDarkhastFaktorNoeHavale);
//        if (!ccDarkhastFaktorNoeFaktor.trim().equals("")) {
//            Log.d("getProgram", "ccDarkhastFaktorNoeFaktor:" + ccDarkhastFaktorNoeFaktor);
//            darkhastFaktorSatrDAO.fetchDarkhastFaktorSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(DarkhastFaktorModel.ccNoeFaktor), ccDarkhastFaktorNoeFaktor, new RetrofitResponse() {
//                @Override
//                public void onSuccess(final ArrayList arrayListData) {
//                    Thread thread = new Thread() {
//                        @Override
//                        public void run() {
//                            Log.d("getProgram", "insert faktor:" + String.valueOf(DarkhastFaktorModel.ccNoeFaktor) + "-" + arrayListData.toString());
//                            //boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
//                            boolean insertResult = darkhastFaktorSatrDAO.insertGroup(arrayListData);
//                            if (deleteResult && insertResult) {
//                                mapResult.put(DarkhastFaktorModel.ccNoeFaktor, true);
//                            } else {
//                                mapResult.put(DarkhastFaktorModel.ccNoeFaktor, false);
//                            }
//                        }
//                    };
//                    thread.start();
//                }
//
//                @Override
//                public void onFailed(String type, String error) {
//                    mPresenter.onFailedGetProgram(++webCounter, String.format(" type : %1$s \n error : %2$s", type, error));
//                }
//            });
//        }
//
//        if (!ccDarkhastFaktorNoeHavale.trim().equals("")) {
//            Log.d("getProgram", "ccDarkhastFaktorNoeHavale:" + ccDarkhastFaktorNoeHavale);
//            darkhastFaktorSatrDAO.fetchDarkhastFaktorSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(DarkhastFaktorModel.ccNoeHavale), ccDarkhastFaktorNoeHavale, new RetrofitResponse() {
//                @Override
//                public void onSuccess(final ArrayList arrayListData) {
//                    Thread thread = new Thread() {
//                        @Override
//                        public void run() {
//                            Log.d("getProgram", "insert Havaleh:" + String.valueOf(DarkhastFaktorModel.ccNoeHavale + "-" + arrayListData.toString()));
//                            //boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
//                            boolean insertResult = darkhastFaktorSatrDAO.insertGroup(arrayListData);
//                            if (deleteResult && insertResult) {
//                                mapResult.put(DarkhastFaktorModel.ccNoeHavale, true);
//                                Log.d("getProgram", "deleteResult success:" + deleteResult + ", insertResult:" + insertResult);
//                            } else {
//                                mapResult.put(DarkhastFaktorModel.ccNoeHavale, false);
//                            }
//
//                        }
//                    };
//                    thread.start();
//                }
//
//                @Override
//                public void onFailed(String type, String error) {
//                    mPresenter.onFailedGetProgram(++webCounter, String.format(" type : %1$s \n error : %2$s", type, error));
//                }
//            });
//        }
//
//        if (mapResult.get(DarkhastFaktorModel.ccNoeFaktor) && mapResult.get(DarkhastFaktorModel.ccNoeHavale)) {
//            Log.d("getProgram", "ccDarkhastFaktorNoeHavale Success");
//            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//            getForoshandehs(getProgramType, ccForoshandehString);
//        } else {
//            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
//        }
//    }

    /**
     * section 39
     **/
    private void getForoshandehsPakhshRx(int getProgramType) {
        ArrayList<String> foroshandeArrayList = new ArrayList<>();
         foroshandehArray = new String[]{};
        if (new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel) != 4) {
            if (foroshandehMamorPakhshModel.getCcForoshandehs().contains(",")) {
                foroshandehArray = foroshandehMamorPakhshModel.getCcForoshandehs().split(",");//از فروشنده مامور پخش
            } else {
                foroshandehArray = new String[]{foroshandehMamorPakhshModel.getCcForoshandehs()};
            }
        } else {
            if (ccForoshandehString.contains(",")) {
                foroshandehArray = ccForoshandehString.split(",");//از فروشنده مامور پخش
            } else {
                foroshandehArray = new String[]{ccForoshandehString};
            }
        }
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(foroshandehArray));
        foroshandehArray = hashSet.toArray(new String[hashSet.size()]);
        foroshandeArrayList = new CollectionUtils().convertArrayToList(foroshandehArray);
        getForoshandehByEachccForoshandehRx(getProgramType, foroshandeArrayList);

    }

    /**
     * section 40
     **/
    private void getForoshandehByEachccForoshandehRx(int getProgramType, ArrayList<String> foroshandeArrayList) {
        ArrayList<ForoshandehModel> foroshandehModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};
        Observable.fromIterable(foroshandeArrayList)
                .flatMap(ccforohandeh -> {
                    return apiServiceRxjava.getAllvForoshandehByccForoshandeh(ccforohandeh)
                            .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "", "getForoshandehByEachccForoshandehRx"));
                }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetAllvForoshandehByccForoshandehResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllvForoshandehByccForoshandehResult> getAllvForoshandehByccForoshandehResultResponse) {
                        foroshandehModels.addAll(getAllvForoshandehByccForoshandehResultResponse.body().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        webCounter[0]++;
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be twentyOne");
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "onNext: onComplete Called");
                        updateForoshandePakhshTable(getProgramType, foroshandeArrayList, foroshandehModels);
                    }
                });
    }

    /**
     * section 41
     **/
    private void updateForoshandePakhshTable(int getProgramType, ArrayList<String> foroshandehArrayList, ArrayList<ForoshandehModel> foroshandehModels) {
        ForoshandehRepository foroshandehRepository = new ForoshandehRepository(mPresenter.getAppContext());
        Disposable disposabledeleteAll = foroshandehRepository.deleteAll().subscribe(deleteAll -> {
            if (deleteAll) {
                Disposable disposableinsertGroup = foroshandehRepository.insertGroup(foroshandehModels)
                        .subscribe(insertGroup -> {
                            if (insertGroup) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("RxJavaRequest", +itemCounter + "accept: updateForoshandeTable");

                                if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD &&
                                        noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM &&
                                        noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART)
                                    getForoshandehApiCallsRx(getProgramType);
                                else //MamorPakhshSard , MamorPakhshSmart , Sarparast , Modir

                                    //for kalaPishnahadi
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getMamorPakhshApiCallsRx(getProgramType, foroshandehArrayList);
                            } else {
                                throwException("updateForoshandeTable");
                            }
                        }, throwable -> throwException("updateForoshandeTable"));
                compositeDisposable.add(disposableinsertGroup);
            } else {
                Log.i("RxJavaRequest", +itemCounter + "accept: updateForoshandeTable" + deleteAll);
                throwException("updateForoshandeTable");
            }
        }, throwable -> throwException("updateForoshandeTable"));
        compositeDisposable.add(disposabledeleteAll);
    }

    /**
     * section 42
     **/
    private void getMamorPakhshApiCallsRx(int getProgramType, ArrayList<String> foroshandehArrayList) {
        final int[] webCounter = {itemCounter};
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        Disposable disposableGetAllForoshandeh = darkhastFaktorRepository.getAllccForoshandeh()
                .subscribe(ccForoshandehString -> {
                    Log.i("RxJavaRequest", +itemCounter + "getMamorPakhshApiCallsRx,ccForoshandehString: ");

                    apiServiceRxjava.getListKalaForMarjoee(ccForoshandehString, ccMoshtarys)
                            .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMamorPakhshApiCallsRx", "getListKalaForMarjoee"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<GetListKalaForMarjoeeResult>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    compositeDisposable.add(d);

                                }

                                @Override
                                public void onNext(@NonNull Response<GetListKalaForMarjoeeResult> getListKalaForMarjoeeResultResponse) {
                                    getRptBargashtyRx(getProgramType, foroshandehArrayList, getListKalaForMarjoeeResultResponse.body());
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                });
        compositeDisposable.add(disposableGetAllForoshandeh);
    }

    private void getRptBargashtyRx(int getProgramType, ArrayList<String> foroshandehArrayList, GetListKalaForMarjoeeResult listKalaForMarjoeeResult) {
        final int[] webCounter = {itemCounter};
        GetListBargashtyForoshandehResult getListBargashtyForoshandehResult;
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
        Observable.fromIterable(foroshandehArrayList)

                .flatMap(ccForoshandeh ->
                        {

                            return apiServiceRxjava.getListBargashtyForoshandeh(ccForoshandeh)
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "", "getRptBargashtyRx"))
                                    .subscribeOn(Schedulers.io());
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetListBargashtyForoshandehResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetListBargashtyForoshandehResult> getListBargashtyForoshandehResultResponse) {
                        bargashtyModels.addAll(getListBargashtyForoshandehResultResponse.body().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        updateListKalaForMarjoeeTable(getProgramType, listKalaForMarjoeeResult, bargashtyModels);
                    }
                });
    }

    /**
     * section 42
     **/
    private void getForoshandehApiCallsRx(int getProgramType) {
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getDarkhastFaktorKalaPishnahadiResult(String.valueOf(ccForoshandeh), ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getForoshandehApiCallsRx", "getDarkhastFaktorKalaPishnahadiResult"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(darkhastFaktorKalaPishnahadiResultResponse -> ++webCounter[0])

                ,

                apiServiceRxjava.getListKalaForMarjoee(String.valueOf(ccForoshandeh), ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getForoshandehApiCallsRx", "getListKalaForMarjoee"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getListKalaForMarjoeeResultResponse -> webCounter[0]++)
                ,

                apiServiceRxjava.getListBargashtyForoshandeh(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getForoshandehApiCallsRx", "getListBargashtyForoshandeh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getListBargashtyForoshandehResultResponse -> webCounter[0]++)

                ,


                (darkhastFaktorKalaPishnahadiResultResponse, getListKalaForMarjoeeResultResponse, getListBargashtyForoshandehResultResponse) -> {

                    updateForoshandehTablesRx(getProgramType, darkhastFaktorKalaPishnahadiResultResponse.body()
                            , getListKalaForMarjoeeResultResponse.body()
                            , getListBargashtyForoshandehResultResponse.body().getData()
                    );


                    return true;

                }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean fetchedAllData) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be twentyFour");
                    }
                });
    }

    /**
     * section 43
     **/
    private void updateForoshandehTablesRx(
            int getProgramType
            , DarkhastFaktorKalaPishnahadiResult darkhastFaktorKalaPishnahadiResult
            , GetListKalaForMarjoeeResult listKalaForMarjoeeResult
            , ArrayList<BargashtyModel> bargashtyModels) {

        updateDarkhastFaktorKalaPishnahadiTable(getProgramType, darkhastFaktorKalaPishnahadiResult
                , listKalaForMarjoeeResult
                , bargashtyModels);

    }

    /**
     * section 44
     **/
    private void updateDarkhastFaktorKalaPishnahadiTable(int getProgramType, DarkhastFaktorKalaPishnahadiResult
            darkhastFaktorKalaPishnahadiResult
            , GetListKalaForMarjoeeResult listKalaForMarjoeeResult
            , ArrayList<BargashtyModel> bargashtyModels) {

        if (darkhastFaktorKalaPishnahadiResult != null) {


            DarkhastFaktorKalaPishnahadiRepository darkhastFaktorKalaPishnahadiRepository = new DarkhastFaktorKalaPishnahadiRepository(mPresenter.getAppContext());
            Disposable disposabledeleteAll = darkhastFaktorKalaPishnahadiRepository.deleteAll().subscribe(deleteAll -> {
                if (deleteAll) {
                    Disposable disposableInsertGroup = darkhastFaktorKalaPishnahadiRepository.insertGroup(darkhastFaktorKalaPishnahadiResult.getData())
                            .subscribe(insertGroup -> {
                                if (insertGroup) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    Log.i("RxJavaRequest", +itemCounter + "accept: updateDarkhastFaktorKalaPishnahadiTable" + deleteAll);
                                    updateListKalaForMarjoeeTable(getProgramType
                                            , listKalaForMarjoeeResult
                                            , bargashtyModels);

                                } else {
                                    throwException("updateDarkhastFaktorKalaPishnahadiTable");
                                }
                            }, throwable -> throwException("updateDarkhastFaktorKalaPishnahadiTable"));
                    compositeDisposable.add(disposableInsertGroup);
                } else {
                    Log.i("RxJavaRequest", +itemCounter + "accept: updateForoshandeTable" + deleteAll);
                    throwException("updateDarkhastFaktorKalaPishnahadiTable");
                }
            }, throwable -> throwException("updateDarkhastFaktorKalaPishnahadiTable"));
            compositeDisposable.add(disposabledeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            updateListKalaForMarjoeeTable(getProgramType
                    , listKalaForMarjoeeResult
                    , bargashtyModels);
        }
    }

    /**
     * section 45
     **/
    private void updateListKalaForMarjoeeTable(int getProgramType
            , GetListKalaForMarjoeeResult listKalaForMarjoeeResult
            , ArrayList<BargashtyModel> bargashtyModels) {
        if (listKalaForMarjoeeResult != null) {
            ListKalaForMarjoeeRepository listKalaForMarjoeeRepository = new ListKalaForMarjoeeRepository(mPresenter.getAppContext());
            Disposable disposabledeleteAll = listKalaForMarjoeeRepository.deleteAll().subscribe(deleteAll -> {
                if (deleteAll) {
                    Disposable disposableinsertGroup = listKalaForMarjoeeRepository.insertGroup(listKalaForMarjoeeResult.getData())
                            .subscribe(insertGroup -> {
                                if (insertGroup) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    Log.i("RxJavaRequest", +itemCounter + "accept: updateListKalaForMarjoeeTable");
                                    updateBargashtyTable(getProgramType, bargashtyModels);
                                } else {
                                    throwException("updateListKalaForMarjoeeTable");
                                }
                            }, throwable -> throwException("updateListKalaForMarjoeeTable"));
                    compositeDisposable.add(disposableinsertGroup);
                } else {
                    Log.i("RxJavaRequest", +itemCounter + "accept: updateForoshandeTable" + deleteAll);
                    throwException("updateListKalaForMarjoeeTable");
                }
            }, throwable -> throwException("updateListKalaForMarjoeeTable"));
            compositeDisposable.add(disposabledeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", +itemCounter + "accept: updateListKalaForMarjoeeTable");
            updateBargashtyTable(getProgramType, bargashtyModels);
        }
    }

    /**
     * section 46
     **/
    private void updateBargashtyTable(int getProgramType, ArrayList<BargashtyModel> bargashtyModels) {


        BargashtyRepository bargashtyRepository = new BargashtyRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = bargashtyRepository.deleteAll().subscribe(deleteAll -> {
            if (deleteAll) {
                Disposable disposableinsertGroup = bargashtyRepository.insertGroup(bargashtyModels)
                        .subscribe(insertGroup -> {
                            if (insertGroup) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("RxJavaRequest", +itemCounter + "accept: updateBargashtyTable");

                                if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                    getMoshtaryChidman(getProgramType, ccMasirs);
                                } else {
                                    getccMoshtaryPakhshForoshandeh(getProgramType);
                                }

                            } else {
                                throwException("updateBargashtyTable");
                            }
                        }, throwable -> throwException("updateBargashtyTable"));
                compositeDisposable.add(disposableinsertGroup);
            } else {
                Log.i("RxJavaRequest", +itemCounter + "accept: updateForoshandeTable" + deleteAll);
                throwException("updateBargashtyTable");
            }
        }, throwable -> throwException("updateBargashtyTable"));
        compositeDisposable.add(disposableDeleteAll);
    }


    /**
     * section 47
     **/
    private void getccMoshtaryPakhshForoshandeh(int getProgramType) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());

        Disposable disposableGetccMoshtaryPakhshForForoshandeh = darkhastFaktorRepository.getccMoshtaryPakhshForForoshandeh(Constants.CODE_VAZEIAT_FAKTOR_TASVIEH)
                .subscribe(map -> {
                    Log.i("RxJavaRequest", "getMoshtariesPakhshRx: ");
                    getMoshtaryPakhshRx(getProgramType, map);
                }, throwable -> throwException("getccMoshtaryPakhshForoshandeh"));

        compositeDisposable.add(disposableGetccMoshtaryPakhshForForoshandeh);
    }


//    int moshtaryPakhshRxCounter = 0;

    /**
     * section 48
     **/
    private void getMoshtaryPakhshRx(int getProgramType, Map<Integer, String> map) {
        ArrayList<MoshtaryModel> moshtaryPakhshModels = new ArrayList<>();
        ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels = new ArrayList<>();
        ArrayList<String> listCodeMoshtary = new ArrayList<>();
        ArrayList<Integer> listccforoshandeh = new ArrayList<>();

        final int[] webCounter = {itemCounter};

        Observable.fromIterable(map.keySet())
                .flatMap(ccForoshandeh -> {
                    String codeMoshtary = map.get(ccForoshandeh);
                    if (codeMoshtary != null && !codeMoshtary.trim().equals("")) {
                        return Observable.zip(apiServiceRxjava.getAllMoshtaryByccMasir(String.valueOf(ccForoshandeh), "-1", codeMoshtary.replace("'", ""))
                                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMoshtaryPakhshRx", "getAllMoshtaryByccMasir"))
                                        .subscribeOn(Schedulers.io())
                                , apiServiceRxjava.getMoshtaryMorajehShodehRooz(String.valueOf(ccForoshandeh), ccMasirs)
                                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMoshtaryPakhshRx", "getMoshtaryMorajehShodehRooz"))
                                        .subscribeOn(Schedulers.io())
                                , (getAllMoshtaryByccMasirResultResponse, moshtaryMorajehShodehRoozResultResponse) -> {
                                    moshtaryPakhshModels.addAll(getAllMoshtaryByccMasirResultResponse.body().getData());
                                    moshtaryMorajehShodehRoozModels.addAll(moshtaryMorajehShodehRoozResultResponse.body().getData());
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("codeMoshtary", codeMoshtary);
                                    jsonObject.put("ccForoshandeh", ccForoshandeh);

                                    listCodeMoshtary.add(codeMoshtary);
                                    listccforoshandeh.add(ccForoshandeh);
                                    return jsonObject;

                                });
                    } else {
                        return null;
                    }

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull JSONObject result) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "getMoshtaryPakhshRx: counterError" + e.getMessage());
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be twentySix");
                        Log.i("RxJavaRequest", "getMoshtaryPakhshRx: onComplete" + itemCounter);

                        updateMoshtaryPakhshTable(getProgramType, listCodeMoshtary, moshtaryPakhshModels, moshtaryMorajehShodehRoozModels);
                    }
                });
    }


    /**
     * section 49
     **/
    private void updateMoshtaryPakhshTable(int getProgramType, ArrayList<String> listCodeMoshtary, ArrayList<MoshtaryModel> moshtaryPakhshModels, ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels) {
        Observable.fromIterable(listCodeMoshtary)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String codeMoshtary) {
                        deleteMoshtaryPakhshTable(getProgramType, codeMoshtary);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        throwException("updateMoshtaryPakhshTable");

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", +itemCounter + "onComplete:getMoshtaryPakhshRx ");
                        bulkInsertMoshtaryPakhshTable(getProgramType, moshtaryPakhshModels, moshtaryMorajehShodehRoozModels);
                    }
                });
    }

    /**
     * section 49
     **/
    private void bulkInsertMoshtaryPakhshTable(int getProgramType, ArrayList<MoshtaryModel> moshtaryPakhshModels, ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());
        Disposable disposableinsertGroup = moshtaryRepository.insertGroup(moshtaryPakhshModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertGroup -> {
                    if (insertGroup) {
                        Disposable disposableUpdateExtraOlaviatFromOlaviat = moshtaryRepository.updateExtraOlaviatFromOlaviat()
                                .subscribe(UpdateExtraOlaviatFromOlaviat ->
                                {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryPakhshTable");
                                    updateMoshtaryMorajeShodeRoozTable(getProgramType, moshtaryMorajehShodehRoozModels);
                                }, throwable ->
                                {
                                    throwException("updateMoshtaryPakhshTable");
                                });
                        compositeDisposable.add(disposableUpdateExtraOlaviatFromOlaviat);

                    } else {
                        throwException("updateMoshtaryPakhshTable");
                    }
                }, throwable -> throwException("updateMoshtaryPakhshTable"));
        compositeDisposable.add(disposableinsertGroup);
    }

    /**
     * section 50
     **/
    private void updateMoshtaryMorajeShodeRoozTable(int getProgramType, ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodeRoozModels) {

        MoshtaryMorajehShodehRoozRepository moshtaryMorajehShodehRoozRepository = new MoshtaryMorajehShodehRoozRepository(mPresenter.getAppContext());
        Disposable disposableDelete = moshtaryMorajehShodehRoozRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {

                    if (deleteAll) {
                        Disposable disposableInsertGroup = moshtaryMorajehShodehRoozRepository.insertGroup(moshtaryMorajehShodeRoozModels)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(InsertGroup -> {
                                    if (InsertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryMorajeShodeRoozTable ");
                                        getAllDarkhastByCodeNoeVaziat(getProgramType);
                                    } else {

                                        throwException("updateMoshtaryMorajeShodeRoozTable");
                                    }

                                }, throwable -> {
                                    throwException("updateMoshtaryMorajeShodeRoozTable");
                                });
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateMoshtaryMorajeShodeRoozTable");
                    }
                }, throwable -> {
                    throwException("updateMoshtaryMorajeShodeRoozTable");
                });
        compositeDisposable.add(disposableDelete);
    }

    /**
     * section 51
     **/
    private void getAllDarkhastByCodeNoeVaziat(int getProgramType) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        Disposable disposableGetAllByNotCodeVazeiat = darkhastFaktorRepository.getAllByNotCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TASVIEH)
                .subscribe(darkhastFaktorModels -> fetchMoshtaryAddressMamorPakhshRx(getProgramType, darkhastFaktorModels), throwable -> throwException("getAllDarkhastByCodeNoeVaziat"));
        compositeDisposable.add(disposableGetAllByNotCodeVazeiat);
    }
//    updateMoshtaryAddressMamorPakhshTable(getProgramType);

    /**
     * section 52
     **/
    private void fetchMoshtaryAddressMamorPakhshRx(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
        final int[] webCounter = {itemCounter};

        ArrayList<MoshtaryAddressModel> moshtaryAddressMamorPakhshModels = new ArrayList<>();
        ArrayList<Integer> ccMoshtaryList = new ArrayList<>();
        Observable.fromIterable(darkhastFaktorModels)
                .filter(darkhastFaktorModel -> !ccMoshtaryList.contains(darkhastFaktorModel.getCcMoshtary()))
                .flatMap(darkhastFaktorModel ->
                {
                        ccMoshtaryList.add(darkhastFaktorModel.getCcMoshtary());
                        return apiServiceRxjava.getMoshtaryAddressByNoeMasouliat(String.valueOf(darkhastFaktorModel.getCcForoshandeh()), "-1", String.valueOf(darkhastFaktorModel.getCcMoshtary()));

                }).compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "", "callMoshtaryAddressMamorPakhshRx"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetAllvMoshtaryAddressResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllvMoshtaryAddressResult> getAllvMoshtaryAddressResultResponse) {
                        if (getAllvMoshtaryAddressResultResponse!=null)
                        moshtaryAddressMamorPakhshModels.addAll(getAllvMoshtaryAddressResultResponse.body().getData());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {


                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be twentySeven"+ccMoshtaryList);
                        updateMoshtaryAddressMamorPakhshTable(getProgramType, ccMoshtaryList, moshtaryAddressMamorPakhshModels);
                    }
                });


    }

    /**
     * section 53
     **/
    private void updateMoshtaryAddressMamorPakhshTable(int getProgramType, ArrayList<Integer> ccMoshtaryList, ArrayList<MoshtaryAddressModel> moshtaryAddressMamorPakhshModels) {
        
        MoshtaryAddressRepository moshtaryAddressRepository = new MoshtaryAddressRepository(mPresenter.getAppContext());
        String ccMoshtaries = new CollectionUtils().convertIntegerArrayToString(ccMoshtaryList);
        Disposable disposableDeleteByCcMoshtary = moshtaryAddressRepository.deleteByccMoshtaries(ccMoshtaries)
                .subscribe(deleteByccMoshtaries -> {
                    Disposable disposableInsertGroup = moshtaryAddressRepository.insertGroup(moshtaryAddressMamorPakhshModels)
                            .subscribe(insertGroup1 -> {
                                if (insertGroup1) 
                                {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryAddressMamorPakhshTable: ");
                                    getAllMoshtaryPakhshApis(getProgramType);
                                }
                                else {
                                    throwException("updateMoshtaryAddressMamorPakhshTable");
                                }

                            }, throwable -> {
                                throwException("updateMoshtaryAddressMamorPakhshTable");
                            });
                    compositeDisposable.add(disposableInsertGroup);


                }, throwable -> throwException("updateMoshtaryAddressMamorPakhshTable"));
        compositeDisposable.add(disposableDeleteByCcMoshtary);

    }

    /**
     * section 54
     **/
    private void getAllMoshtaryPakhshApis(int getProgramType) {

        final GetAllvMoshtaryAfradResult[] moshtaryAfradResult = new GetAllvMoshtaryAfradResult[1];
        final GetAllvMoshtaryShomarehHesabResult[] moshtaryShomarehHesabResult = new GetAllvMoshtaryShomarehHesabResult[1];
        final GetAllvMoshtaryEtebarSazmanForoshResult[] moshtaryEtebarSazmanForoshResult = new GetAllvMoshtaryEtebarSazmanForoshResult[1];
        final int[] webCounter = {itemCounter};

        Observable.zip(apiServiceRxjava.getAllvMoshtaryAfrad(ccMoshtaryPakhsh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllMoshtaryApis", "getAllvMoshtaryAfrad"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryAfradResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllvMoshtaryShomarehHesab(ccMoshtaryPakhsh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllMoshtaryApis", "getAllvMoshtaryShomarehHesab"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryShomarehHesabResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllvMoshtaryEtebarSazmanForoshForPakhsh(ccMoshtaryPakhsh, String.valueOf(ccSazmanForoshPakhsh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllMoshtaryApis", "getAllvMoshtaryEtebarSazmanForoshForPakhsh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvMoshtaryEtebarSazmanForoshResultResponse -> webCounter[0]++)
                , (getAllvMoshtaryAfradResultResponse, getAllvMoshtaryShomarehHesabResultResponse, getAllvMoshtaryEtebarSazmanForoshResultResponse) -> {
                    if (getAllvMoshtaryAfradResultResponse.isSuccessful() && getAllvMoshtaryShomarehHesabResultResponse.isSuccessful() && getAllvMoshtaryEtebarSazmanForoshResultResponse.isSuccessful()) {
                        moshtaryAfradResult[0] = getAllvMoshtaryAfradResultResponse.body();
                        moshtaryShomarehHesabResult[0] = getAllvMoshtaryShomarehHesabResultResponse.body();
                        moshtaryEtebarSazmanForoshResult[0] = getAllvMoshtaryEtebarSazmanForoshResultResponse.body();

                        return true;
                    }

                    return false;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean fetchAllMoshtary) {

                        updateMoshtaryGorohTable(getProgramType, moshtaryAfradResult[0]
                                , moshtaryShomarehHesabResult[0]
                                , moshtaryEtebarSazmanForoshResult[0]);


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthy");

                    }
                });

    }


    /**
     * section 55
     **/
    private void updateMoshtaryGorohTable(int getProgramType, GetAllvMoshtaryAfradResult getAllvMoshtaryAfradResult
            , GetAllvMoshtaryShomarehHesabResult getAllvMoshtaryShomarehHesabResult
            , GetAllvMoshtaryEtebarSazmanForoshResult getAllvMoshtaryEtebarSazmanForoshResult) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        updateMoshtaryAfradPakhshTable(getProgramType, getAllvMoshtaryAfradResult, getAllvMoshtaryShomarehHesabResult, getAllvMoshtaryEtebarSazmanForoshResult);

    }


    /**
     * section 56
     **/
    private void updateMoshtaryAfradPakhshTable(int getProgramType, GetAllvMoshtaryAfradResult getAllvMoshtaryAfradResult
            , GetAllvMoshtaryShomarehHesabResult getAllvMoshtaryShomarehHesabResult
            , GetAllvMoshtaryEtebarSazmanForoshResult getAllvMoshtaryEtebarSazmanForoshResult) {

        Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryAfradPakhshTable: ");
        Observable.fromIterable(getAllvMoshtaryAfradResult.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoshtaryAfradModel>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull MoshtaryAfradModel moshtaryAfradModel) {

                        ccAfradPakhsh += "," + moshtaryAfradModel.getCcAfrad();
                        ;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryAfradPakhshTable:error ");

                    }

                    @Override
                    public void onComplete() {

//
                        if (getAllvMoshtaryAfradResult != null) {
                            MoshtaryAfradRepository moshtaryAfradRepository = new MoshtaryAfradRepository(mPresenter.getAppContext());
                            Disposable deleteGroup = moshtaryAfradRepository.deleteGroup(ccAfradPakhsh)
                                    .subscribe(deleteGroup1 -> {
                                        if (deleteGroup1) {
                                            Disposable insertGroup = moshtaryAfradRepository.insertGroup(getAllvMoshtaryAfradResult.getData())
                                                    .subscribe(insertGroup1 -> {
                                                        if (insertGroup1) {
                                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                                            updateMoshtaryPakhshShomarehHesabTable(getProgramType, getAllvMoshtaryShomarehHesabResult, getAllvMoshtaryEtebarSazmanForoshResult);
                                                            Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryAfradPakhshTable: ");
                                                        } else {
                                                            throwException("updateMoshtaryAfradPakhshTable");
                                                        }

                                                    }, throwable -> throwException("updateMoshtaryAfradPakhshTable"));
                                            compositeDisposable.add(insertGroup);
                                        } else {
                                            throwException("updateMoshtaryAfradPakhshTable");

                                        }

                                    });
                            compositeDisposable.add(deleteGroup);

                        } else {

                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            updateMoshtaryPakhshShomarehHesabTable(getProgramType, getAllvMoshtaryShomarehHesabResult, getAllvMoshtaryEtebarSazmanForoshResult);
                            Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryAfradPakhshTable: ");
                        }
                    }
//
//
                });


    }

    /**
     * section 57
     **/
    private void updateMoshtaryPakhshShomarehHesabTable(int getProgramType
            , GetAllvMoshtaryShomarehHesabResult moshtaryShomarehHesabModels
            , GetAllvMoshtaryEtebarSazmanForoshResult moshtaryEtebarSazmanForoshModels) {
        if (moshtaryShomarehHesabModels != null) {
            MoshtaryShomarehHesabRepository moshtaryShomarehHesabRepository = new MoshtaryShomarehHesabRepository(mPresenter.getAppContext());
            Disposable disposableInsertGroup = moshtaryShomarehHesabRepository.insertGroup(moshtaryShomarehHesabModels.getData())
                    .subscribe(insertGroup ->
                    {
                        if (insertGroup) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            updateMoshtaryEtebarSazmanForoshPakhshTable(getProgramType, moshtaryEtebarSazmanForoshModels);
                            Log.i("RxJavaRequest", +itemCounter + "accept: updateMoshtaryPakhshShomarehHesabTable");

                        } else {
                            throwException("updateMoshtaryPakhshShomarehHesabTable");
                        }

                    }, throwable -> {
                        throwException("updateMoshtaryPakhshShomarehHesabTable");

                    });

            compositeDisposable.add(disposableInsertGroup);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            updateMoshtaryEtebarSazmanForoshPakhshTable(getProgramType, moshtaryEtebarSazmanForoshModels);
            Log.i("RxJavaRequest", +itemCounter + "accept: updateMoshtaryPakhshShomarehHesabTable");
        }
    }

    /**
     * section 58
     **/
    private void updateMoshtaryEtebarSazmanForoshPakhshTable(int getProgramType, GetAllvMoshtaryEtebarSazmanForoshResult getAllvMoshtaryEtebarSazmanForoshResult) {
        if (getAllvMoshtaryEtebarSazmanForoshResult != null) {
            MoshtaryEtebarSazmanForoshRepository moshtaryEtebarSazmanForoshRepository = new MoshtaryEtebarSazmanForoshRepository(mPresenter.getAppContext());
            Disposable disposableInsertGroup = moshtaryEtebarSazmanForoshRepository.insertGroup(getAllvMoshtaryEtebarSazmanForoshResult.getData())
                    .subscribe(insertGroup -> {
                        if (insertGroup) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            prepareDataForGetDariaftPardakhtRx(getProgramType);
                            Log.i("RxJavaRequest", +itemCounter + "accept: updateMoshtaryEtebarSazmanForoshPakhshTable");

                        } else {
                            throwException("updateMoshtaryEtebarSazmanForoshPakhshTable");

                        }
                    });
            compositeDisposable.add(disposableInsertGroup);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            prepareDataForGetDariaftPardakhtRx(getProgramType);

            Log.i("RxJavaRequest", +itemCounter + "accept: updateMoshtaryEtebarSazmanForoshPakhshTable");
        }
    }


    private void deleteMoshtaryPakhshTable(int getProgramType, String codeMoshtary) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = moshtaryRepository.deleteByCodeMoshtarys(codeMoshtary).subscribe(deleteByCodeMoshtarys -> {
            if (deleteByCodeMoshtarys) {

            }
        }, throwable -> throwException("deleteMoshtaryPakhshTable"));
        compositeDisposable.add(disposableDeleteAll);
    }


    private void getMoshtaryGorohPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMoshtaryShomareHesabPakhsh(getProgramType, ccMoshtaryPakhsh);
    }


    private void getMoshtaryShomareHesabPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        final MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        moshtaryShomarehHesabDAO.fetchAllvMoshtaryShomarehHesab(mPresenter.getAppContext(), activityNameForLog, ccMoshtaryPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = moshtaryShomarehHesabDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryEtebarPakhsh(getProgramType, ccMoshtaryPakhsh, String.valueOf(ccSazmanForosh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryEtebarPakhsh(final int getProgramType, String ccMoshtaryPakhsh, String ccSazmanForosh) {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForoshForPakhsh(mPresenter.getAppContext(), activityNameForLog, ccMoshtaryPakhsh, ccSazmanForoshPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            getDariaftPardakht(getProgramType, ccDarkhastFaktors);
                            Log.i("RxJavaRequest", +itemCounter + "run:getMoshtaryEtebarPakhsh ");
//                            prepareDataForGetDariaftPardakhtRx(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    /**
     * section 59
     **/
    private void prepareDataForGetDariaftPardakhtRx(int getProgramType) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        DariaftPardakhtPPCRepository dariaftPardakhtPPCRepository = new DariaftPardakhtPPCRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = dariaftPardakhtPPCRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable getAllByNoeFaktorHavaleAndNotCodeVazeiatDisposable = darkhastFaktorRepository.getAllByNoeFaktorHavaleAndNotCodeVazeiat(1, Constants.CODE_VAZEIAT_FAKTOR_TASVIEH)
                                .subscribe(darkhastFaktorModels ->
                                        {
                                            GetDariaftPardakhtRx(getProgramType, darkhastFaktorModels);
                                        }
                                        , throwable -> throwException("prepareDataForGetDariaftPardakhtRx"));
                        compositeDisposable.add(getAllByNoeFaktorHavaleAndNotCodeVazeiatDisposable);
                    } else {
                        throwException("prepareDataForGetDariaftPardakhtRx");
                    }
                }, throwable -> {
                    throwException("prepareDataForGetDariaftPardakhtRx");

                });
        compositeDisposable.add(disposableDeleteAll);

    }

//    int dariaftPardakhtCounter = 0;

    /**
     * section 60
     * <p>
     * change this
     **/
    private void GetDariaftPardakhtRx(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {

        final int[] dariaftPardakhtCounter = {0};
        final int[] webCounter = {itemCounter};
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        Observable.fromIterable(darkhastFaktorModels)
                .subscribeOn(Schedulers.io())
                .flatMap(darkhastFaktorModel ->
                {
                    return apiServiceRxjava.getDariaftPardakhtHavalePPC("1", "-1," + String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()))
                            .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "GetDariaftPardakhtRx", "getDariaftPardakhtHavalePPC"))
                            .subscribeOn(Schedulers.io());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetDariaftPardakhtHavalePPCResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onNext(@NonNull Response<GetDariaftPardakhtHavalePPCResult> getDariaftPardakhtHavalePPCResultResponse) {

                        if (getDariaftPardakhtHavalePPCResultResponse.body() != null) {
                            if (getDariaftPardakhtHavalePPCResultResponse.body().getData() != null) {
                                if (getDariaftPardakhtHavalePPCResultResponse.body().getData().size() > 0) {
                                    ccDariaftPardakhts += getDariaftPardakhtHavalePPCResultResponse.body().getData().get(0).getCcDariaftPardakht() + ",";
                                    if (ccDariaftPardakhts.endsWith(",")) {
                                        ccDariaftPardakhts = ccDariaftPardakhts.substring(0, ccDariaftPardakhts.length() - 1);
                                    }
                                }
                            }
                            dariaftPardakhtPPCModels.addAll(getDariaftPardakhtHavalePPCResultResponse.body().getData());
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                        dariaftPardakhtCounter[0]++;

//                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        if (dariaftPardakhtCounter[0] < 2) {
                            Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthyOne");
                            updateDariaftPardakhtPPCTable(getProgramType, ccDariaftPardakhts, dariaftPardakhtPPCModels, darkhastFaktorModels);
                        }


                    }
                });


    }

    /**
     * section 62
     **/
    private void updateDariaftPardakhtDarkhastFaktorTable(int getProgramType, ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels) {

        DariaftPardakhtDarkhastFaktorPPCRepository dariaftPardakhtDarkhastFaktorPPCRepository = new DariaftPardakhtDarkhastFaktorPPCRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = dariaftPardakhtDarkhastFaktorPPCRepository.deleteAll()
                .subscribe(aBoolean -> {
                    Disposable disposable = dariaftPardakhtDarkhastFaktorPPCRepository.insertGroup(dariaftPardakhtDarkhastFaktorPPCModels, true)

                            .subscribe(insertGroup -> {
                                if (insertGroup) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    updateDarkhastFaktorSatrTakhfifTable(getProgramType);
                                    Log.i("RxJavaRequest", +itemCounter + "updateDariaftPardakhtDarkhastFaktorTable :");
                                } else {
                                    Log.i("RxJavaRequest", +itemCounter + "updateDariaftPardakhtDarkhastFaktorTable fail :" + insertGroup);
                                    throwException("updateDariaftPardakhtDarkhastFaktorTable");
                                }
                            }, throwable -> {
                                Log.i("RxJavaRequest", +itemCounter + "run:updateDariaftPardakhtDarkhastFaktorTable fail:" + throwable.getMessage());

                                throwException("updateDariaftPardakhtDarkhastFaktorTable");
                            });
                    compositeDisposable.add(disposable);
                }, throwable -> throwException("updateDariaftPardakhtDarkhastFaktorTable"));
        compositeDisposable.add(disposableDeleteAll);

    }


    /**
     * section 61
     **/
    private void updateDariaftPardakhtPPCTable(int getProgramType, String ccDariaftPardakhts, ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {

        DariaftPardakhtPPCRepository dariaftPardakhtPPCRepository = new DariaftPardakhtPPCRepository(mPresenter.getAppContext());
        Disposable disposabledelete = dariaftPardakhtPPCRepository.deleteByccDariaftPardakhts(ccDariaftPardakhts)
                .subscribe(deleteByccDariaftPardakhts -> {
                    if (deleteByccDariaftPardakhts) {
                        Disposable disposableInsertGroup = dariaftPardakhtPPCRepository.insertGroup(dariaftPardakhtPPCModels)
                                .subscribe(insertGroup -> {
                                    if (!insertGroup) {
                                        throwException("updateDariaftPardakhtPPCTable");
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateDariaftPardakhtPPCTable");
                                        getDariaftPardakhtDarkhastFaktorPPC(getProgramType, darkhastFaktorModels);
//                                        updateDariaftPardakhtDarkhastFaktorTable(getProgramType,dariaftPardakhtDarkhastFaktorPPCModels);
                                    }


                                }, throwable -> throwException("updateDariaftPardakhtPPCTable"));
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateDariaftPardakhtPPCTable");
                    }
                }, throwable -> throwException("updateDariaftPardakhtPPCTable"));
        compositeDisposable.add(disposabledelete);

    }


    private void getDariaftPardakhtDarkhastFaktorPPC(int getProgramType, ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
        final int[] dariaftPardakhtDarkhastFaktorCounter = {0};
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};
        Observable.fromIterable(darkhastFaktorModels)
                .flatMap(darkhastFaktorModel ->
                {
                    return apiServiceRxjava.getDariaftPardakhtDarkhastFaktorHavalehPPC("1", String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()))
                            .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "GetDariaftPardakhtRx", "getDariaftPardakhtDarkhastFaktorHavalehPPC"))
                            .subscribeOn(Schedulers.io());
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> getDariaftPardakhtDarkhastFaktorHavalehPPCResultResponse) {


                        if (getDariaftPardakhtDarkhastFaktorHavalehPPCResultResponse.body() != null) {
                            dariaftPardakhtDarkhastFaktorPPCModels.addAll(getDariaftPardakhtDarkhastFaktorHavalehPPCResultResponse.body().getData());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                        dariaftPardakhtDarkhastFaktorCounter[0]++;
                        if (dariaftPardakhtDarkhastFaktorCounter[0] < 2) {
                            Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthyTwo");
                            updateDariaftPardakhtDarkhastFaktorTable(getProgramType, dariaftPardakhtDarkhastFaktorPPCModels);
                        }
                    }
                });


    }

    /**
     * section 63
     **/
    private void updateDarkhastFaktorSatrTakhfifTable(int getProgramType) {
        DarkhastFaktorSatrTakhfifRepository darkhastFaktorSatrTakhfifRepository = new DarkhastFaktorSatrTakhfifRepository(mPresenter.getAppContext());
        DarkhastFaktorTakhfifRepository darkhastFaktorTakhfifRepository = new DarkhastFaktorTakhfifRepository(mPresenter.getAppContext());

        Disposable disposableFaktorSatr = darkhastFaktorSatrTakhfifRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {

                        Disposable disposableFaktor = darkhastFaktorTakhfifRepository.deleteAll()
                                .subscribe(deleteAll1 -> {

                                    if (deleteAll1) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "run:updateDarkhastFaktorSatrTakhfifTable :");
                                        getAllccGoroh(getProgramType);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "run:updateDarkhastFaktorSatrTakhfifTable  fail:" + deleteAll1);
                                        throwException("updateDarkhastFaktorSatrTakhfifTable");

                                    }

                                }, throwable -> {
                                    throwException("updateDarkhastFaktorSatrTakhfifTable");
                                    Log.i("RxJavaRequest", +itemCounter + "run:updateDarkhastFaktorSatrTakhfifTable  fail:");

                                });
                        compositeDisposable.add(disposableFaktor);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "run:updateDarkhastFaktorSatrTakhfifTable  fail:");
                        throwException("updateDarkhastFaktorSatrTakhfifTable");
                    }

                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "run:updateDarkhastFaktorSatrTakhfifTable :" + throwable.getMessage());

                    throwException("updateDarkhastFaktorSatrTakhfifTable");
                });
        compositeDisposable.add(disposableFaktorSatr);
    }

    /**
     * section 64
     **/
    private void getAllccGoroh(int getProgramType) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());
        Disposable disposableGetAllccNoeSenf = moshtaryRepository.getAllccNoeSenf()
                .subscribe(s -> {
                    ccGorohs.set(s);
                    ccGorohss = s;
                    prepareModatVosolApis(getProgramType);


                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "run:getAllccGoroh fail:" + throwable.getMessage());
                    throwException("getAllccGoroh");
                });
        compositeDisposable.add(disposableGetAllccNoeSenf);

    }

    /**
     * section 65
     **/
    private void prepareModatVosolApis(int getProgramType) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());


        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD && noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            Disposable disposableGetccMarkazSazmanForoshSakhtarForosh = darkhastFaktorRepository.getccMarkazSazmanForoshSakhtarForoshAll()
                    .subscribe(ccMarkazSazmanSakhtarForoshs ->
                    {
                        getModatVosolApis(getProgramType, ccMarkazSazmanSakhtarForoshs);


                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "run:prepareModatVosolApis fail:" + throwable.getMessage());
                        throwException("prepareModatVosolApis");
                    });
            compositeDisposable.add(disposableGetccMarkazSazmanForoshSakhtarForosh);
        } else {
            ArrayList<Integer> ccMarkazSazmanSakhtarForoshHa = new ArrayList<>();
            ccMarkazSazmanSakhtarForoshHa.add(ccMarkazSazmanForoshSakhtarForosh);
            Log.i("RxJavaRequest", +itemCounter + "prepareModatVosolApis: " + ccMarkazSazmanSakhtarForoshHa.size());
            getModatVosolApis(getProgramType, ccMarkazSazmanSakhtarForoshHa);

        }
    }

    /**
     * section 66
     **/
    private void getModatVosolApis(int getProgramType, ArrayList<Integer> ccMarkazSazmanSakhtarForoshHa) {
        ArrayList<ModatVosolModel> modateVosolModels = new ArrayList<>();
        ArrayList<ModatVosolGorohModel> modateVosolGorohModels = new ArrayList<>();
        ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};
        int stepCounter = 0;
        Observable.fromIterable(ccMarkazSazmanSakhtarForoshHa)
                .flatMap(ccMarkazSazmanSakhtarForosh -> {

                    return Observable.zip(apiServiceRxjava.getAllvModatVosolByccMarkazForoshGorohRx(ccMarkazSazmanSakhtarForosh, ccGorohs.get())
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getVosolApis", "getAllvModatVosolByccMarkazForoshGoroh"))
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext(getAllvModatVosolByccMarkazForoshGorohResultResponse -> {
                                                if (stepCounter < 3)
                                                    webCounter[0]++;
                                            }
                                    )


                            , apiServiceRxjava.getAllModatVosolGoroh(ccGorohs.get())
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getVosolApis", "getAllModatVosolGoroh"))
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext(getAllModatVosolGorohResultResponse -> {
                                        if (stepCounter < 3)
                                            webCounter[0]++;

                                    })


                            , apiServiceRxjava.getAllModatVosolMarkazForoshByccMarkazForoshRx(ccMarkazSazmanSakhtarForosh)
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getVosolApis", "getAllModatVosolMarkazForoshByccMarkazForoshRx"))
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext(getAllModatVosolMarkazForoshByccMarkazForoshResultResponse -> {
                                        if (stepCounter < 3)
                                            webCounter[0]++;
                                    })


                            , (getAllvModatVosolByccMarkazForoshGorohResultResponse, getAllModatVosolGorohResultResponse, getAllModatVosolMarkazForoshByccMarkazForoshResultResponse) -> {
                                modateVosolModels.addAll(getAllvModatVosolByccMarkazForoshGorohResultResponse.body().getData());
                                modateVosolGorohModels.addAll(getAllModatVosolGorohResultResponse.body().getData());
                                modatVosolMarkazModels.addAll(getAllModatVosolMarkazForoshByccMarkazForoshResultResponse.body().getData());
                                return true;
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "onSubscribe:getModatVosolApis ");

                    }

                    @Override
                    public void onNext(@NonNull Boolean getModatVosolApis) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        //three individual api calls

                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthyFive");
                        updateModatVosolTable(getProgramType, modateVosolModels, modateVosolGorohModels, modatVosolMarkazModels);
                    }
                });

    }

    /**
     * section 67
     **/
    private void updateModatVosolTable(int getProgramType, ArrayList<ModatVosolModel> modatVosolModels, ArrayList<ModatVosolGorohModel> modatVosolGorohModels, ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {
        ModatVosolRepository modatVosolRepository = new ModatVosolRepository(mPresenter.getAppContext());
        Disposable disposableMaodatVosolDelete = modatVosolRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableMaodatVosolInsertGroup = modatVosolRepository.insertGroup(modatVosolModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolTable: " + insertGroup);
                                        updateModatVosolGorohTable(getProgramType, modatVosolGorohModels, modatVosolMarkazModels);
                                    } else {
                                        throwException("updateModatVosolTable");
                                    }

                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolTable fail: " + throwable.getMessage());
                                    throwException("updateModatVosolTable");
                                });
                        compositeDisposable.add(disposableMaodatVosolInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolTable:error ");
                        throwException("updateModatVosolTable");
                    }
                }, throwable -> Log.i("RxJavaRequest", +itemCounter + "updateModatVosolTable fail: " + throwable.getMessage()));
        compositeDisposable.add(disposableMaodatVosolDelete);
    }

    /**
     * section 68
     **/
    private void updateModatVosolGorohTable(int getProgramType
            , ArrayList<ModatVosolGorohModel> modatVosolGorohModels
            , ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {

        ModatVosolGorohRepository modatVosolGorohRepository = new ModatVosolGorohRepository(mPresenter.getAppContext());
        Disposable disposableMaodatVosolGorohDelete = modatVosolGorohRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableMaodatVosolGorohInsertGroup = modatVosolGorohRepository.insertGroup(modatVosolGorohModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolGorohTable:");
                                        updateModatVosolMarkazTable(getProgramType, modatVosolMarkazModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolGorohTable: fail " + deleteAll);

                                        throwException("updateModatVosolGorohTable");
                                    }

                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolGorohTable: fail " + throwable.getMessage());
                                    throwException("updateModatVosolGorohTable");
                                });
                        compositeDisposable.add(disposableMaodatVosolGorohInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolGorohTable:fail");
                        throwException("updateModatVosolGorohTable");
                    }
                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolGorohTable: " + throwable.getMessage());
                    throwException("updateModatVosolGorohTable");
                });
        compositeDisposable.add(disposableMaodatVosolGorohDelete);

    }

    /**
     * section 69
     **/
    private void updateModatVosolMarkazTable(int getProgramType, ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {
        ModatVosolMarkazRepository modatVosolMarkazRepository = new ModatVosolMarkazRepository(mPresenter.getAppContext());
        Disposable disposableMaodatVosolGorohDelete = modatVosolMarkazRepository.deleteAll()
                .subscribe(deleteAll ->
                {
                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable: " + deleteAll);
                    if (deleteAll) {
                        Disposable disposableMaodatVosolGorohInsertGroup = modatVosolMarkazRepository.insertGroup(modatVosolMarkazModels)
                                .subscribe(insertGroup ->
                                {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable");
                                        getAnbarakAfradRx(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable: fail" + deleteAll);
                                        throwException("updateModatVosolMarkazTable");
                                    }

                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable:fail " + throwable.getMessage());
                                    throwException("updateModatVosolMarkazTable");
                                });
                        compositeDisposable.add(disposableMaodatVosolGorohInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable:fail ");
                        throwException("updateModatVosolMarkazTable");
                    }
                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateModatVosolMarkazTable:fail " + throwable.getMessage());
                    throwException("updateModatVosolMarkazTable");

                });
        compositeDisposable.add(disposableMaodatVosolGorohDelete);
    }

    /**
     * section 70
     **/
    private void getAnbarakAfradRx(int getProgramType) {
        final int[] webCounter = {itemCounter};

        apiServiceRxjava.getAnbarakAfrad(String.valueOf(ccAfrad))
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "", "getAnbarakAfrad"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetAnbarakAfradResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAnbarakAfradResult> getAnbarakAfradResultResponse) {
                        updateCcAnbarakAfrad(getProgramType, getAnbarakAfradResultResponse.body().getData());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        ++webCounter[0];
                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthySix");
                    }
                });
    }

    /**
     * section 71
     **/
    private void updateCcAnbarakAfrad(int getProgramType, ArrayList<AnbarakAfradModel> anbarakAfradModels) {
        Observable.fromIterable(anbarakAfradModels)
                .map(anbarakAfradModel -> {

                    return anbarakAfrad += anbarakAfradModel.getCcAnbarak();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", "anbarakAfrad onComplete: " + anbarakAfrad);
                        updateAnbarakInfoTable(getProgramType, anbarakAfradModels);
                    }
                });
    }


    /**
     * section 72
     **/

    private void updateAnbarakInfoTable(int getProgramType, ArrayList<AnbarakAfradModel> anbarakAfradModels) {
        AnbarakAfradRepository anbarakAfradRepository = new AnbarakAfradRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = anbarakAfradRepository.deleteAll()

                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = anbarakAfradRepository.insertGroup(anbarakAfradModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {

//                                                getKala(getProgramType, String.valueOf(ccAfrad), String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                                        } else {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                                                       getEtebar(getProgramType);
                                            Log.i("RxJavaRequest", +itemCounter + "updateAnbarakInfoTable ");
                                            getEtebarRx(getProgramType);
                                        }

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "accept:updateAnbarakInfoTable ");
                                        throwException("updateAnbarakInfoTable");

                                    }

                                }, throwable ->
                                        throwException("updateAnbarakInfoTable"));
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateAnbarakInfoTable");
                    }
                }, throwable -> throwException("updateAnbarakInfoTable"));
        compositeDisposable.add(disposableDeleteAll);
    }

    /**
     * section 73
     **/
    private void getEtebarRx(int getProgramType) {


        ForoshandehRepository foroshandehRepository = new ForoshandehRepository(mPresenter.getAppContext());
        EtebarRepository etebarRepository = new EtebarRepository(mPresenter.getAppContext());
        Disposable disposableDistinctForoshandeh = foroshandehRepository.getDistinctccForoshandeh()
                .subscribe(strings -> etebarRepository.deleteByCcForohsandehString(new CollectionUtils().convertStringArrayToString(strings))
                        .subscribe(new Observer<ArrayList<String>>() {
                            @Override
                            public void onSubscribe( Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext( ArrayList<String> ccForohsandehStrings) {
                             fetchEtebarForoshandehRx(getProgramType,ccForohsandehStrings);
                            }

                            @Override
                            public void onError( Throwable e) {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                            }

                            @Override
                            public void onComplete() {

                            }
                        }), throwable ->
                {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                });
        compositeDisposable.add(disposableDistinctForoshandeh);


    }

    private void fetchEtebarForoshandehRx(int getProgramType,ArrayList<String> ccForoshandehStrings) {
        final int[] webCounter = {itemCounter};
        ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels = new ArrayList<>();

        Observable.fromIterable(ccForoshandehStrings)
              .flatMap(ccForoshandeh -> {
            return apiServiceRxjava.getEtebarForoshandeh(ccForoshandeh).subscribeOn(Schedulers.io());
        })
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getEtebarRx", "getEtebarForoshandeh"))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetEtebarForoshandehResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetEtebarForoshandehResult> getEtebarForoshandehResultResponse) {

                        foroshandehEtebarModels.addAll(getEtebarForoshandehResultResponse.body().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "getEtebarRx: getDistinctccForoshandeh" + throwable.getMessage());

                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", throwable.getCause().getMessage(), throwable.getMessage()));
                    }

                    @Override
                    public void onComplete() {


                        Log.i("webCounterLog", "getSecondAmbush: " + webCounter[0] + "\t must be thirthySeven");
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        updateEtebarForoshandehTable(getProgramType, foroshandehEtebarModels);

                    }
                });

    }

    /**
     * section 74
     **/
    private void updateEtebarForoshandehTable(int getProgramType, ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels) {
        ForoshandehEtebarRepository etebarRepository = new ForoshandehEtebarRepository(mPresenter.getAppContext());
        Disposable disposableInsertGroup = etebarRepository.insertGroup(foroshandehEtebarModels)
                .subscribe(insertGroup ->
                {
                    if (insertGroup) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        Log.i("RxJavaRequest", +itemCounter + "updateEtebarForoshandehTable\" ");


                        getccMarkazForoshBaseOnNoeMasouliat(getProgramType);

                    } else {
                        throwException("updateEtebarForoshandehTable");
                    }

                }, throwable -> throwException("updateEtebarForoshandehTable"));

        compositeDisposable.add(disposableInsertGroup);

    }

    private void getccMarkazForoshBaseOnNoeMasouliat(int getProgramType) {
        String ccMarkazForoshForKalaZaribForosh = "";

        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            ccMarkazForoshForKalaZaribForosh = ccMarkazForoshPakhsh;
            Log.i("RxJavaRequest", "getccMarkazForoshBaseOnNoeMasouliat: " + ccMarkazForoshForKalaZaribForosh);

        } else {

            ccMarkazForoshForKalaZaribForosh = String.valueOf(ccMarkazForosh);
            Log.i("RxJavaRequest", "getccMarkazForoshBaseOnNoeMasouliat: " + ccMarkazForoshForKalaZaribForosh);

        }

        getAllKalaApis(getProgramType, ccMarkazForoshForKalaZaribForosh);
    }


//    private void getEtebarForoshandeh(final int getProgramType) {
//        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//
//
//    }

    /**
     * section 76
     **/
    private void getccMamorPakhshAnbarakAfradBaseOnNoeMasouliat(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        ForoshandehMamorPakhshRepository foroshandehMamorPakhshRepository = new ForoshandehMamorPakhshRepository(mPresenter.getAppContext());
        Disposable disposableGetForoshandehMamorPakhsh = foroshandehMamorPakhshRepository.getForoshandehMamorPakhsh()
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(foroshandehMamorPakhshModel -> {
                    ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
                    String ccSazmanForosh = String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh());
                    String ccKalaCode = "-1";
                    if (noeMasouliat == 1)//1-Foroshandeh-Sard
                    {
                        anbarakAfrad = "0";
                        ccMamorPakhsh = 0;
                    } else if (noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm // 3-Foroshandeh-Smart
                    {
                        ccMamorPakhsh = 0;
                    } else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
                    {
                        ccForoshandeh = 0;
                    } else //6-SarparastForoshandeh 7-Amargar
                    {
                        ccForoshandeh = 0;
                        ccMamorPakhsh = 0;
                    }


                    getMandehMojodiMashinAndKalaOlaviat(getProgramType, anbarakAfrad, ccForoshandeh, ccMamorPakhsh, ccKalaCode, ccSazmanForosh, kalaModels, kalaGorohModels, kalaZaribForoshModels);


                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "accept: getccMamorPakhshAnbarakAfradBaseOnNoeMasouliat" + throwable.getMessage());
                    throwException("getccMamorPakhshAnbarakAfradBaseOnNoeMasouliat");
                });
        compositeDisposable.add(disposableGetForoshandehMamorPakhsh);

    }

    /**
     * section 77
     **/
    private void getMandehMojodiMashinAndKalaOlaviat(int getProgramType, String anbarakAfrad, int ccForoshandeh, int ccMamorPakhsh, String ccKalaCode, String ccSazmanForosh, ArrayList<KalaModel> kalaModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        final GetMandehMojodyMashinResponse[] getMandehMojodyMashinResponse = new GetMandehMojodyMashinResponse[1];
        final GetKalaOlaviatResult[] getKalaOlaviatResult = new GetKalaOlaviatResult[1];
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getKalaOlaviat(anbarakAfrad)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMandehMojodiMashinAndKalaOlaviat", "getKalaOlaviat"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getKalaOlaviatResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getMandehMojodyMashin(anbarakAfrad, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh), ccKalaCode, ccSazmanForosh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMandehMojodiMashinAndKalaOlaviat", "getMandehMojodyMashin"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMandehMojodyMashinResponseResponse -> webCounter[0]++)
                , (getKalaOlaviatResultResponse, getMandehMojodyMashinResponseResponse) -> {
                    getKalaOlaviatResult[0] = getKalaOlaviatResultResponse.body();
                    getMandehMojodyMashinResponse[0] = getMandehMojodyMashinResponseResponse.body();

                    return true;


                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllApis) {
                        updateMandehMojodiMashinTable(getProgramType, kalaModels, getMandehMojodyMashinResponse[0], getKalaOlaviatResult[0], kalaGorohModels, kalaZaribForoshModels);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", "onComplete: updateMandehMojodiAnbarTable");


                    }
                });
    }

    /**
     * section 78
     **/
    private void updateMandehMojodiMashinTable(int getProgramType, ArrayList<KalaModel> kalaModels, GetMandehMojodyMashinResponse mandehMojodyMashinResponse, GetKalaOlaviatResult kalaOlaviatResult, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        if (mandehMojodyMashinResponse != null) {
            MandehMojodyMashinRepository mandehMojodyMashinRepository = new MandehMojodyMashinRepository(BaseApplication.getContext());
            Disposable disposableDeleteAll = mandehMojodyMashinRepository.deleteAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable disposableInsertGroup = mandehMojodyMashinRepository.insertGroup(mandehMojodyMashinResponse.getMandehMojodyMashinModels())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "onNext: updateMandehMojodiMashinTable" + itemCounter);
                                            updateKalaOlaviatTable(getProgramType, kalaModels, kalaOlaviatResult, kalaGorohModels, kalaZaribForoshModels);

                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiMashinTable:error1");
                                            throwException("updateMandehMojodiMashinTable");

                                        }


                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiMashinTable:" + throwable.getMessage());
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);


                                    });
                            compositeDisposable.add(disposableInsertGroup);
                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiMashinTable:error3");
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                        }

                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiMashinTable" + throwable.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    });
            compositeDisposable.add(disposableDeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "onNext: updateMandehMojodiMashinTable" + itemCounter);
            updateKalaOlaviatTable(getProgramType, kalaModels, kalaOlaviatResult, kalaGorohModels, kalaZaribForoshModels);
        }

    }

    /**
     * section 75
     **/
    private void getAllKalaApis(int getProgramType, String ccMarkazForosh) {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};

        Log.i("webCounterRx", "getAllKalaApis:webCounter " + webCounter[0]);
        Observable.zip(apiServiceRxjava.getMojodyAnbar(String.valueOf(ccAfrad), String.valueOf(ccMarkazSazmanForoshSakhtarForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllKalaApis", "getMojodyAnbar"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMojodyAnbarResultResponse -> {
                                webCounter[0]++;

                        })
                , apiServiceRxjava.getAllvKalaGoroh()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllKalaApis", "getAllvKalaGoroh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvKalaGorohResultResponse -> {
                                webCounter[0]++;
                        })
                        .doOnComplete(() -> Log.i("webCounterRx", "onNext:webCounter " + webCounter[0]))
                , apiServiceRxjava.getAllvKalaZaribForosh(ccGorohs.get(), String.valueOf(ccMarkazForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllKalaApis", "getAllvKalaZaribForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvKalaZaribForoshResultResponse ->
                        {
                                webCounter[0]++;

                        })
                , (getMojodyAnbarResultResponse, getAllvKalaGorohResultResponse, getAllvKalaZaribForoshResultResponse) -> {
                    if (getMojodyAnbarResultResponse.isSuccessful()
                            && getAllvKalaZaribForoshResultResponse.isSuccessful()) {

                        kalaModels.addAll(getMojodyAnbarResultResponse.body().getData());
                        kalaGorohModels.addAll(getAllvKalaGorohResultResponse.body().getData());
                        kalaZaribForoshModels.addAll(getAllvKalaZaribForoshResultResponse.body().getData());


                        return true;
                    }
                    return false;
                }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllKalaApis) {

                        if (getAllKalaApis) {
                            if (anbarakAfrad.trim().equals("-1")) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "kalaOlaviat ");

                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "mandehMojodiMashin ");

                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "mandehMojodiAnbar ");
                                updateKalaTable(getProgramType
                                        , kalaModels
                                        , kalaGorohModels
                                        , kalaZaribForoshModels);

                            } else {

                                getccMamorPakhshAnbarakAfradBaseOnNoeMasouliat(getProgramType, kalaModels
                                        , kalaGorohModels
                                        , kalaZaribForoshModels);
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * section 76
     **/
    private void updateKalaTable(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        KalaRepository kalaRepository = new KalaRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = kalaRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = kalaRepository.insertGroup(kalaModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateKala");

                                        updateKalaGorohTable(getProgramType, kalaGorohModels, kalaZaribForoshModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiAnbarTable:error1");
                                        throwException("updateKalaTable");
                                    }


                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiAnbarTable:" + throwable.getMessage());
                                    throwException("updateKalaTable");
                                });
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiAnbarTable:error3");
                        throwException("updateKalaTable");

                    }

                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateMandehMojodiAnbarTable" + throwable.getMessage());
                    throwException("updateKalaTable");

                });
        compositeDisposable.add(disposableDeleteAll);

    }

    /**
     * section 79
     **/
    private void updateKalaOlaviatTable(int getProgramType, ArrayList<KalaModel> kalaModels, GetKalaOlaviatResult kalaOlaviatResult, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        if (kalaOlaviatResult != null) {
            KalaOlaviatRepository kalaOlaviatRepository = new KalaOlaviatRepository(mPresenter.getAppContext());
            Disposable disposableDeleteAll = kalaOlaviatRepository.deleteAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable disposableInsertGroup = kalaOlaviatRepository.insertGroup(kalaOlaviatResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", +itemCounter + "updateKalaOlaviatTable:");
                                            getKalaMojodiRx(getProgramType, kalaModels, kalaGorohModels, kalaZaribForoshModels);
                                        } else {
                                            throwException("updateKalaOlaviatTable");
                                        }

                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateKalaOlaviatTable:" + throwable.getMessage());
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                                    });
                            compositeDisposable.add(disposableInsertGroup);
                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "updateKalaOlaviatTable:deleteAll error");
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                        }

                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "updateKalaOlaviatTable:" + throwable.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    });
            compositeDisposable.add(disposableDeleteAll);
        } else {

            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", +itemCounter + "updateKalaOlaviatTable:");
            getKalaMojodiRx(getProgramType, kalaModels, kalaGorohModels, kalaZaribForoshModels);

        }
    }

    /**
     * section 78
     **/
    private void updateKalaGorohTable(int getProgramType, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {

        KalaGorohRepository kalaGorohRepository = new KalaGorohRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = kalaGorohRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = kalaGorohRepository.insertGroup(kalaGorohModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + " \t updateKalaGorohTable");
                                        updateKalaZaribForoshTable(getProgramType, kalaZaribForoshModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateKalaGorohTable: insertGroup error");
                                        throwException("updateKalaGorohTable");
                                    }

                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateKalaGorohTable: insertGroup error" + throwable.getMessage());
                                    throwException("updateKalaGorohTable");
                                });
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateKalaGorohTable: insertGroup error");
                        throwException("updateKalaGorohTable");
                    }


                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateKalaGorohTable: insertGroup error" + throwable.getMessage());
                    throwException("updateKalaGorohTable");
                });
        compositeDisposable.add(disposableDeleteAll);
    }

    /**
     * section 83
     **/
    private void updateKalaZaribForoshTable(int getProgramType, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        KalaZaribForoshRepository kalaZaribForoshRepository = new KalaZaribForoshRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = kalaZaribForoshRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = kalaZaribForoshRepository.insertGroup(kalaZaribForoshModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateKalaZaribForoshTable");

//                                        getJayezeh(getProgramType);
                                        getAllJayezehApi(getProgramType);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateKalaZaribForoshTable: insertGroup error");
                                        throwException("updateKalaZaribForoshTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateKalaZaribForoshTable: insertGroup error" + throwable.getMessage());
                                });
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateKalaZaribForoshTable: insertGroup error");
                        throwException("updateKalaZaribForoshTable");
                    }
                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateKalaZaribForoshTable: insertGroup error" + throwable.getMessage());
                    throwException("updateKalaZaribForoshTable");
                });
        compositeDisposable.add(disposableDeleteAll);
    }


    private void throwException(String cause) {
        try {
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
            Logger logger = new Logger();
            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), String.format("%1$s in %2$s ", "Exception in SQL transactions", cause), this.getClass().getSimpleName(), ACTIVITY_NAME, "throwException", cause);
            throw new Throwable("Exception in SQL transactions", new Throwable(cause));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * section 80
     **/
    private void getKalaMojodiRx(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        KalaMojodiRepository kalaMojodiRepository = new KalaMojodiRepository(mPresenter.getAppContext());
        kalaMojodiRepository.deleteAll()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean deleteAll) {
                        if (deleteAll) {
                            getMandehMojodiMashinFromDB(getProgramType, kalaModels, kalaGorohModels, kalaZaribForoshModels);
                        } else {
                            throwException("getKalaMojodiRx");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * section 81
     **/
    private void getMandehMojodiMashinFromDB(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        MandehMojodyMashinRepository mandehMojodyMashinRepository = new MandehMojodyMashinRepository(mPresenter.getAppContext());

        mandehMojodyMashinRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<MandehMojodyMashinModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {

                        if (mandehMojodyMashinModels.size() > 0) {
                            updateKalaMojodiRx(getProgramType, kalaModels, mandehMojodyMashinModels, kalaGorohModels, kalaZaribForoshModels);
                        } else {
                            //updateKalaMojodiTable
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            updateKalaTable(getProgramType, kalaModels, kalaGorohModels, kalaZaribForoshModels);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * section 82
     **/
    private void updateKalaMojodiRx(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        KalaMojodiRepository kalaMojodiRepository = new KalaMojodiRepository(mPresenter.getAppContext());
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        Disposable deleteKalaMojodi = kalaMojodiRepository.deleteAll()
                .subscribe(deleteKalaMojodi1 -> {
                    if (deleteKalaMojodi1) {
                        Observable.fromIterable(mandehMojodyMashinModels)
                                .map(mandehMojodyMashinModel -> {
                                    KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                                    kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
                                    kalaMojodiModel.setCcForoshandeh(ccForoshandeh);
                                    kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                                    kalaMojodiModel.setCcDarkhastFaktor(0);
                                    kalaMojodiModel.setTarikhDarkhast(currentDate);
                                    kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
                                    kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
                                    kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
                                    kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
                                    kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
                                    kalaMojodiModel.setZamaneSabt(currentDate);
                                    kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
                                    kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
                                    kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
                                    kalaMojodiModel.setCcAfrad(ccAfrad);
                                    return kalaMojodiModel;
                                }).subscribeOn(Schedulers.io())
                                .subscribe(new Observer<KalaMojodiModel>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onNext(@NonNull KalaMojodiModel kalaMojodiModel) {
                                        kalaMojodiModels.add(kalaMojodiModel);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.i("RxJavaRequest", +itemCounter + "onComplete: updateKalaMojodi" + e.getMessage());
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);


                                    }

                                    @Override
                                    public void onComplete() {
                                        updateKalaMojodiTable(getProgramType, kalaModels, kalaMojodiModels, kalaGorohModels, kalaZaribForoshModels);

                                    }
                                });

                    } else {
                        throwException("updateKalaMojodiRx");
                    }

                }, throwable -> sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter));
        compositeDisposable.add(deleteKalaMojodi);
    }

    /**
     * section 83
     **/
    private void updateKalaMojodiTable(int getProgramType, ArrayList<KalaModel> kalaModels, ArrayList<KalaMojodiModel> kalaMojodiModels, ArrayList<KalaGorohModel> kalaGorohModels, ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
        KalaMojodiRepository kalaMojodiRepository = new KalaMojodiRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = kalaMojodiRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = kalaMojodiRepository.insertGroup(kalaMojodiModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateKalaMojodiTable: insertGroup");

                                        updateKalaTable(getProgramType, kalaModels, kalaGorohModels, kalaZaribForoshModels);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "updateKalaMojodiTable: insertGroup error");
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "updateKalaMojodiTable: insertGroup error" + throwable.getMessage());
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                });
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "updateKalaMojodiTable: insertGroup error");
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                    }
                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "updateKalaMojodiTable: insertGroup error" + throwable.getMessage());
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                });
        compositeDisposable.add(disposableDeleteAll);

    }


    /**
     * section 84
     **/
    private void getAllJayezehApi(final int getProgramType) {
        ArrayList<JayezehModel> jayezehModels = new ArrayList<>();
        ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels = new ArrayList<>();


        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        String[] ccSazmanForoshha;
        if (ccMarkazSazmanForosh.contains(",")) {
            ccSazmanForoshha = ccMarkazSazmanForosh.split(",");//از فروشنده مامور پخش
        } else {
            ccSazmanForoshha = new String[]{ccMarkazSazmanForosh};
        }
        ArrayList<String> sazmanForoshArray = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(ccSazmanForoshha));
        ccSazmanForoshha = hashSet.toArray(new String[hashSet.size()]);
        sazmanForoshArray = new CollectionUtils().convertArrayToList(ccSazmanForoshha);
        final int[] webCounter = {itemCounter};
        int stepCounter = 0;

        Observable.fromIterable(sazmanForoshArray)
                .flatMap(ccSazmanForosh -> {
                    return Observable.zip(apiServiceRxjava.getAllvJayezehEntekhabi(ccSazmanForosh)
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllJayezehApi", "getAllvJayezehEntekhabi"))
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext(getAllvJayezehEntekhabiResultResponse -> {
                                        if (stepCounter < 2) {
                                            webCounter[0]++;
                                        }

                                    })
                            , apiServiceRxjava.getJayezeh("1", ccSazmanForosh, "-1")
                                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getJayezeh", "getAllvJayezehEntekhabi"))
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext(getAllvJayezehByccMarkazForoshResultResponse -> {
                                        if (stepCounter < 2) {
                                            webCounter[0]++;
                                        }

                                    })
                            , (getAllvJayezehEntekhabiResultResponse, getAllvJayezehByccMarkazForoshResultResponse) -> {

                                jayezehModels.addAll(getAllvJayezehByccMarkazForoshResultResponse.body().getData());
                                jayezehEntekhabiModels.addAll(getAllvJayezehEntekhabiResultResponse.body().getData());
                                return true;
                            });
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllJayezehApi) {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "onNext: " + e.getMessage());
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        getAllCcJayezeh(getProgramType, jayezehModels, jayezehEntekhabiModels);


                    }
                });


    }

    /**
     * section 85
     **/
    private void getJayezehSatrRx(int getProgramType, String ccJayezehs) {
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getJayezehSatr("2", ccMarkazSazmanForosh, ccJayezehs)
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getJayezehSatr", ""))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetAllvJayezehSatrResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllvJayezehSatrResult> getAllvJayezehSatrResultResponse) {
                        updateJayezehSatrTable(getProgramType, getAllvJayezehSatrResultResponse.body().getData());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * section 86
     **/
    private void getAllCcJayezeh(int getProgramType, ArrayList<JayezehModel> jayezehModels, ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        Observable.fromIterable(jayezehModels)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JayezehModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull JayezehModel jayezehModel) {


                        ccJayezehs += "," + jayezehModel.getCcJayezeh();
                        ccJayezehList.add(String.valueOf(jayezehModel.getCcJayezeh()));
                        Log.i("RxJavaRequest", +itemCounter + "getAllCcJayezeh onNext: " + ccJayezehs);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "getAllCcJayezeh onError: " + e.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {

                        Log.i("RxJavaRequest", +itemCounter + "getAllCcJayezeh onComplete: ");
                        updateJayezehTable(getProgramType, jayezehModels, jayezehEntekhabiModels);
                    }
                });
    }

    /**
     * section 87
     **/
    private void updateJayezehTable(int getProgramType, ArrayList<JayezehModel> jayezehModels, ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        JayezehRepository jayezehRepository = new JayezehRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = jayezehRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = jayezehRepository.insertGroup(jayezehModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateJayezehTable:insertGroup " + insertGroup + " ");
                                        Log.i("RxJavaRequest", +itemCounter + "updateJayezehTable: ");
                                        updateJayezehEntekhabiTable(getProgramType, jayezehEntekhabiModels);
                                    } else {
                                        throwException("updateJayezehTable");
                                        Log.i("RxJavaRequest", +itemCounter + "updateJayezehTable: " + deleteAll + " ");
                                    }
                                }, throwable -> throwException("updateJayezehTable"));
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateJayezehTable");
                        Log.i("RxJavaRequest", +itemCounter + "updateJayezehTable: " + deleteAll + " ");
                    }

                }, throwable -> throwException("updateJayezehTable"));
        compositeDisposable.add(disposableDeleteAll);
    }

    /**
     * section 88
     **/
    private void updateMablagheForoshJayeze(int getProgramType, ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
       ArrayList<Integer> ccKalaCodes = new ArrayList<>();
        Observable.fromIterable(jayezehEntekhabiModels)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JayezehEntekhabiModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull JayezehEntekhabiModel jayezehEntekhabiModel) {
                        ccKalaCodes.add(jayezehEntekhabiModel.getCcKalaCode());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "onError: updateMablagheForoshJayeze" + e.getMessage());
                        throwException("updateMablagheForoshJayeze");
                    }

                    @Override
                    public void onComplete() {
                        updateKalaByMaxMojodi(getProgramType,ccKalaCodes,jayezehEntekhabiModels);

                        Log.i("RxJavaRequest", +itemCounter + " updateMablagheForoshJayeze");
                        ArrayList<String> ccJayezeArrayList = new ArrayList<String>();
                        ccJayezeArrayList.addAll(ccJayezehList);
                        ccJayezehs = new CollectionUtils().convertStringArrayToString(ccJayezeArrayList);
                        getJayezehSatrRx(getProgramType, ccJayezehs);

//
                    }
                });


    }

    /**
     * section 89
     **/
    private void updateKalaByMaxMojodi(int getProgramType,ArrayList<Integer> cckalaCodes,ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        new KalaRepository(mPresenter.getAppContext()).getKalaByMaxMojodyAll(cckalaCodes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<KalaModel>>() {
                    @Override
                    public void onSubscribe( Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext( ArrayList<KalaModel> cckalaModels) {
                        updateMablaghForosh(getProgramType,cckalaModels,jayezehEntekhabiModels);
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

                    }

                    @Override
                    public void onError( Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * section 90
     **/
    private void updateMablaghForosh(int getProgramType,ArrayList<KalaModel> kalaModels,ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        JayezehEntekhabiRepository jayezehEntekhabiRepository = new JayezehEntekhabiRepository(mPresenter.getAppContext());
        jayezehEntekhabiRepository.updateMablaghForoshAll(kalaModels)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe( Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext( Boolean updateMablaghForosh) {
                         if (!updateMablaghForosh)
                             throwException("updateMablaghForosh");


//                             updateJayezehEntekhabiTable(getProgramType,jayezehEntekhabiModels);


                    }

                    @Override
                    public void onError( Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    /**
     * section 91
     **/
    private void updateJayezehEntekhabiTable(int getProgramType, ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels) {
        Log.i("RxJavaRequest", "updateJayezehEntekhabiTable: "+jayezehEntekhabiModels.size());
        JayezehEntekhabiRepository jayezehEntekhabiRepository = new JayezehEntekhabiRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = jayezehEntekhabiRepository.deleteAll()
               .compose(RxDAOUtils.parseSQlErrors(CLASS_NAME,"updateJayezehEntekhabiTable","updateJayezehEntekhabiTable","deleteAll"))
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = jayezehEntekhabiRepository.insertGroup(jayezehEntekhabiModels)
                                .compose(RxDAOUtils.parseSQlErrors(CLASS_NAME,"updateJayezehEntekhabiTable","updateJayezehEntekhabiTable","insertGroup"))

                                .subscribe(insertGroup -> {
                                    if (insertGroup) {


                                        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", +itemCounter + "updateJayezehEntekhabiTable: ");
                                            updateMablagheForoshJayeze(getProgramType, jayezehEntekhabiModels);
//                                            getNoeHesab(getProgramType);
                                        } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE() || getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                            //getTakhfifHajmi(getProgramType , ccMarkazSazmanForosh);
//                                            getTakhfifHajmi(getProgramType);

                                        }

                                    } else {
                                        throwException("updateJayezehEntekhabiTable");
                                    }
                                }, throwable -> sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter));
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateJayezehEntekhabiTable");

                    }

                }, throwable -> throwException("updateJayezehEntekhabiTable"));
        compositeDisposable.add(disposableDeleteAll);
    }

    /**
     * section 92
     **/
    private void updateJayezehSatrTable(int getProgramType, ArrayList<JayezehSatrModel> jayezehSatrModels) {
        JayezehSatrRepository jayezehSatrRepository = new JayezehSatrRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = jayezehSatrRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable disposableInsertGroup = jayezehSatrRepository.insertGroup(jayezehSatrModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateJayezehSatrTable");
                                        getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx(getProgramType);
                                    } else {
                                        throwException("updateJayezehSatrTable");
                                    }
                                }, throwable -> throwException("updateJayezehSatrTable"));
                        compositeDisposable.add(disposableInsertGroup);
                    } else {
                        throwException("updateJayezehSatrTable");
                    }

                }, throwable -> throwException("updateJayezehSatrTable"));
        compositeDisposable.add(disposableDeleteAll);
    }


    private void getJayezeh(final int getProgramType) {
        final JayezehDAO jayezehDAO = new JayezehDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        jayezehDAO.fetchAllJayezeh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        String ccJayezehs = "-1";
                        boolean deleteResult = jayezehDAO.deleteAll();
                        boolean insertResult = jayezehDAO.insertGroup(arrayListData);
                        int counter = itemCounter;
                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                            counter = itemCounter;
                        } else {
                            counter = ++itemCounter;
                        }
                        if (deleteResult && insertResult) {
                            for (Object model : arrayListData) {
                                ccJayezehs += "," + ((JayezehModel) model).getCcJayezeh();
                            }
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), counter);
                            getJayezehSatr(getProgramType, ccJayezehs);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), counter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        });
    }

    private void getJayezehSatr(final int getProgramType, String ccJayezehs) {
        final JayezehSatrDAO jayezehSatrDAO = new JayezehSatrDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        jayezehSatrDAO.fetchJayezehSatr(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, ccJayezehs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = jayezehSatrDAO.deleteAll();
                        boolean insertResult = jayezehSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getJayezehEntekhabi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
        /*jayezehSatrDAO.fetchJayezehSatr(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = jayezehSatrDAO.deleteAll();
                        boolean insertResult = jayezehSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getJayezehSatr(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }

    private void getJayezehEntekhabi(final int getProgramType) {
        final JayezehEntekhabiDAO jayezehEntekhabiDAO = new JayezehEntekhabiDAO(mPresenter.getAppContext());
        if (noeMasouliat != 4) {
            jayezehEntekhabiDAO.fetchJayezehEntekhabi(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForosh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = jayezehEntekhabiDAO.deleteAll();
                            boolean insertResult = jayezehEntekhabiDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    JayezehEntekhabiModel jayezehEntekhabiModel = (JayezehEntekhabiModel) arrayListData.get(i);
                                    double mablaghForosh = kalaDAO.getKalaByMaxMojody(jayezehEntekhabiModel.getCcKalaCode()).getMablaghForosh();
                                    if (mablaghForosh > 0) {
                                        jayezehEntekhabiDAO.updateMablaghForosh(mablaghForosh, jayezehEntekhabiModel.getCcKalaCode());
                                    }
                                }
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                    getNoeHesab(getProgramType);
                                } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE() || getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                    //getTakhfifHajmi(getProgramType , ccMarkazSazmanForosh);
                                    getTakhfifHajmi(getProgramType);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            jayezehEntekhabiDAO.fetchJayezehEntekhabiForPakhsh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = jayezehEntekhabiDAO.deleteAll();
                            boolean insertResult = jayezehEntekhabiDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    JayezehEntekhabiModel jayezehEntekhabiModel = (JayezehEntekhabiModel) arrayListData.get(i);
                                    double mablaghForosh = kalaDAO.getKalaByMaxMojody(jayezehEntekhabiModel.getCcKalaCode()).getMablaghForosh();
                                    if (mablaghForosh > 0) {
                                        jayezehEntekhabiDAO.updateMablaghForosh(mablaghForosh, jayezehEntekhabiModel.getCcKalaCode());
                                    }
                                }
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                    getNoeHesab(getProgramType);

                                } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE() || getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                    //getTakhfifHajmi(getProgramType , ccMarkazSazmanForosh);
                                    getTakhfifHajmi(getProgramType);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }

    private void getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx(int getProgramType) {

        final GetAllNoeHesabResult[] noeHesabModels = {null};
        final GetAllNoeMalekiatMoshtaryResult[] getAllNoeMalekiatMoshtaryResult = {null};
        final GetTedadFaktorMoshtaryResult[] getTedadFaktorMoshtaryResult = {null};

        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getAllNoeHesab()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx", "getAllNoeHesab"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllNoeHesabResultResponse -> ++webCounter[0])
                , apiServiceRxjava.getAllNoeMalekiatMoshtary()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx", "getAllNoeMalekiatMoshtary"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllNoeMalekiatMoshtaryResultResponse -> ++webCounter[0])
                , apiServiceRxjava.getTedadFaktorMoshtary(ccMoshtarys)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx", "getTedadFaktorMoshtary"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getTedadFaktorMoshtaryResultResponse -> ++webCounter[0])
                , (Function3<Response<GetAllNoeHesabResult>, Response<GetAllNoeMalekiatMoshtaryResult>, Response<GetTedadFaktorMoshtaryResult>, Boolean>) (getAllNoeHesabResultResponse, getAllNoeMalekiatMoshtaryResultResponse, getTedadFaktorMoshtaryResultResponse) -> {

                    noeHesabModels[0] = getAllNoeHesabResultResponse.body();
                    getAllNoeMalekiatMoshtaryResult[0] = getAllNoeMalekiatMoshtaryResultResponse.body();
                    getTedadFaktorMoshtaryResult[0] = getTedadFaktorMoshtaryResultResponse.body();

                    return true;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aboolean) {
                        updateNoeHesabTable(getProgramType, noeHesabModels[0], getAllNoeMalekiatMoshtaryResult[0], getTedadFaktorMoshtaryResult[0]);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void updateNoeHesabTable(int getProgramType, GetAllNoeHesabResult getAllNoeHesabResultResponse
            , GetAllNoeMalekiatMoshtaryResult getAllNoeMalekiatMoshtaryResultResponse
            , GetTedadFaktorMoshtaryResult getTedadFaktorMoshtaryResult) {
        if (getAllNoeHesabResultResponse != null) {
            NoeHesabRepository noeHesabRepository = new NoeHesabRepository(mPresenter.getAppContext());

            Disposable disposableDeleteAll = noeHesabRepository.deleteAll()
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable disposableInsertGroup = noeHesabRepository.insertGroup(getAllNoeHesabResultResponse.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "updateNoeHesabTable");
                                            updateNoeMalekiatMoshtaryTable(getProgramType, getAllNoeMalekiatMoshtaryResultResponse, getTedadFaktorMoshtaryResult);
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "updateNoeHesabTable: insertGroup error");
                                            throwException("updateNoeHesabTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateNoeHesabTable: insertGroup error" + throwable.getMessage());
                                        throwException("updateNoeHesabTable");

                                    });
                            compositeDisposable.add(disposableInsertGroup);
                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "updateNoeHesabTable: insertGroup error");
                            throwException("updateNoeHesabTable");

                        }

                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "updateNoeHesabTable: insertGroup error" + throwable.getMessage());
                        throwException("updateNoeHesabTable");
                    });
            compositeDisposable.add(disposableDeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "updateNoeHesabTable");
            updateNoeMalekiatMoshtaryTable(getProgramType, getAllNoeMalekiatMoshtaryResultResponse, getTedadFaktorMoshtaryResult);
        }
    }

    private void updateNoeMalekiatMoshtaryTable(int getProgramType, GetAllNoeMalekiatMoshtaryResult noeMalekiatMoshtaryResult, GetTedadFaktorMoshtaryResult tedadFaktorMoshtaryResult) {
        if (noeMalekiatMoshtaryResult != null) {

            NoeMalekiatMoshtaryRepository noeMalekiatMoshtaryRepository = new NoeMalekiatMoshtaryRepository(mPresenter.getAppContext());

            Disposable disposableDeleteAll = noeMalekiatMoshtaryRepository.deleteAll()
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable disposableInsertGroup = noeMalekiatMoshtaryRepository.insertGroup(noeMalekiatMoshtaryResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateNoeMalekiatMoshtaryTable");
                                            updateTedadFaktorMoshtaryTable(getProgramType, tedadFaktorMoshtaryResult);
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "updateNoeMalekiatMoshtaryTable: insertGroup error");
                                            throwException("updateNoeMalekiatMoshtaryTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateNoeMalekiatMoshtaryTable: insertGroup error" + throwable.getMessage());
                                        throwException("updateNoeMalekiatMoshtaryTable");
                                    });
                            compositeDisposable.add(disposableInsertGroup);
                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "updateNoeMalekiatMoshtaryTable: insertGroup error");
                            throwException("updateNoeMalekiatMoshtaryTable");
                        }

                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "updateNoeMalekiatMoshtaryTable: insertGroup error" + throwable.getMessage());
                        throwException("updateNoeMalekiatMoshtaryTable");
                    });
            compositeDisposable.add(disposableDeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateNoeMalekiatMoshtaryTable");
            updateTedadFaktorMoshtaryTable(getProgramType, tedadFaktorMoshtaryResult);
        }

    }

    private void updateTedadFaktorMoshtaryTable(int getProgramType, GetTedadFaktorMoshtaryResult tedadFaktorMoshtaryResult) {
        if (tedadFaktorMoshtaryResult != null) {
            TedadFaktorMoshtaryRepository tedadFaktorMoshtaryRepository = new TedadFaktorMoshtaryRepository(mPresenter.getAppContext());

            Disposable disposableDeleteAll = tedadFaktorMoshtaryRepository.deleteAll()
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable disposableInsertGroup = tedadFaktorMoshtaryRepository.insertGroup(tedadFaktorMoshtaryResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateTedadFaktorMoshtaryTable ");
                                            getAllTakhfifTitrRx(getProgramType);
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "updateTedadFaktorMoshtaryTable: insertGroup error");
                                            throwException("updateTedadFaktorMoshtaryTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateNoeMalekiatMoshtaryTable: insertGroup error" + throwable.getMessage());
                                        throwException("updateTedadFaktorMoshtaryTable");
                                    });
                            compositeDisposable.add(disposableInsertGroup);
                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "updateTedadFaktorMoshtaryTable: insertGroup error");
                            throwException("updateTedadFaktorMoshtaryTable");
                        }

                    }, throwable -> {
                        Log.i("RxJavaRequest", +itemCounter + "updateTedadFaktorMoshtaryTable: insertGroup error" + throwable.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    });
            compositeDisposable.add(disposableDeleteAll);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateTedadFaktorMoshtaryTable ");
            getAllTakhfifTitrRx(getProgramType);
        }
    }


    private void getNoeHesab(final int getProgramType) {
        final NoeHesabDAO noeHesabDAO = new NoeHesabDAO(mPresenter.getAppContext());
        noeHesabDAO.fetchNoeHesab(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeHesabDAO.deleteAll();
                        boolean insertResult = noeHesabDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeMalekiatMoshtary(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeMalekiatMoshtary(final int getProgramType) {
        final NoeMalekiatMoshtaryDAO noeMalekiatMoshtaryDAO = new NoeMalekiatMoshtaryDAO(mPresenter.getAppContext());
        noeMalekiatMoshtaryDAO.fetchNoeMalekiatMoshtary(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeMalekiatMoshtaryDAO.deleteAll();
                        boolean insertResult = noeMalekiatMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTedadFaktorMoshtary(getProgramType, ccMoshtarys);

                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTedadFaktorMoshtary(final int getProgramType, String ccMoshtarys) {
        final TedadFaktorMoshtaryDAO tedadFaktorMoshtaryDAO = new TedadFaktorMoshtaryDAO(mPresenter.getAppContext());
        tedadFaktorMoshtaryDAO.fetchTedadFaktorMoshtary(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = tedadFaktorMoshtaryDAO.deleteAll();
                        boolean insertResult = tedadFaktorMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            //getTakhfifHajmi(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                            getTakhfifHajmi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllTakhfifTitrRx(final int getProgramType) {

        final GetAllvTakhfifHajmiByccMarkazForoshResult[] getAllvTakhfifHajmiByccMarkazForoshResult = new GetAllvTakhfifHajmiByccMarkazForoshResult[1];
        final GetAllvTakhfifSenfiByccMarkazForoshResult[] getAllvTakhfifSenfiByccMarkazForoshResult = new GetAllvTakhfifSenfiByccMarkazForoshResult[1];
        final GetAllvTakhfifNaghdyByccMarkazForoshResult[] getAllvTakhfifNaghdyByccMarkazForoshResult = new GetAllvTakhfifNaghdyByccMarkazForoshResult[1];
        String ccMarkazSazmanForosh = "-1";
        final int[] webCounter = {itemCounter};

        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        Observable.zip(apiServiceRxjava.getTakhfifHajmiTitr("1", ccMarkazSazmanForosh, ccTakhfifHajmis)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllTakhfifTitrRx", "getTakhfifHajmiTitr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvTakhfifHajmiByccMarkazForoshResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getTakhfifNaghdy(ccMarkazSazmanForosh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllTakhfifTitrRx", "getTakhfifNaghdy"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvTakhfifHajmiByccMarkazForoshResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getTakhfifSenfiTitr("1", ccMarkazSazmanForosh, "-1")
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllTakhfifTitrRx", "getTakhfifSenfiTitr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvTakhfifHajmiByccMarkazForoshResultResponse -> webCounter[0]++)
                , (getAllvTakhfifHajmiByccMarkazForoshResultResponse, getAllvTakhfifNaghdyByccMarkazForoshResultResponse, getAllvTakhfifSenfiByccMarkazForoshResultResponse) -> {
                    getAllvTakhfifHajmiByccMarkazForoshResult[0] = getAllvTakhfifHajmiByccMarkazForoshResultResponse.body();
                    getAllvTakhfifNaghdyByccMarkazForoshResult[0] = getAllvTakhfifNaghdyByccMarkazForoshResultResponse.body();
                    getAllvTakhfifSenfiByccMarkazForoshResult[0] = getAllvTakhfifSenfiByccMarkazForoshResultResponse.body();


                    return true;

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllTakhfifTitrRx) {
                        getAllccTakhfifs(getProgramType, getAllvTakhfifHajmiByccMarkazForoshResult[0], getAllvTakhfifNaghdyByccMarkazForoshResult[0], getAllvTakhfifSenfiByccMarkazForoshResult[0]);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "apply: " + e.getMessage());
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + " apply: compelete");
                    }
                });

    }

    private void updateAllTakhfifTables(int getProgramType
            , GetAllvTakhfifHajmiByccMarkazForoshResult getAllvTakhfifHajmiByccMarkazForoshResult
            , GetAllvTakhfifNaghdyByccMarkazForoshResult getAllvTakhfifNaghdyByccMarkazForoshResult
            , GetAllvTakhfifSenfiByccMarkazForoshResult getAllvTakhfifSenfiByccMarkazForoshResult) {

        updateTakhfifHajmiTable(getProgramType, getAllvTakhfifHajmiByccMarkazForoshResult, getAllvTakhfifNaghdyByccMarkazForoshResult, getAllvTakhfifSenfiByccMarkazForoshResult);
    }


    private void getAllccTakhfifs(int getProgramType, GetAllvTakhfifHajmiByccMarkazForoshResult getAllvTakhfifHajmiByccMarkazForoshResult, GetAllvTakhfifNaghdyByccMarkazForoshResult getAllvTakhfifNaghdyByccMarkazForoshResult, GetAllvTakhfifSenfiByccMarkazForoshResult getAllvTakhfifSenfiByccMarkazForoshResult) {
        if (getAllvTakhfifHajmiByccMarkazForoshResult != null) {
            getCcTakhfifHajmisRx(getAllvTakhfifHajmiByccMarkazForoshResult.getData());
        }
        if (getAllvTakhfifSenfiByccMarkazForoshResult != null) {
            getccTakhfifSenfiRx(getAllvTakhfifSenfiByccMarkazForoshResult.getData());
        }
        updateAllTakhfifTables(getProgramType, getAllvTakhfifHajmiByccMarkazForoshResult
                , getAllvTakhfifNaghdyByccMarkazForoshResult
                , getAllvTakhfifSenfiByccMarkazForoshResult);

    }


    private void getCcTakhfifHajmisRx(ArrayList<TakhfifHajmiModel> takhfifHajmiModels) {


        Observable.fromIterable(takhfifHajmiModels)
                .filter(takhfifHajmiModel -> !ccTakhfifHajmiList.contains(String.valueOf(takhfifHajmiModel.getCcTakhfifHajmi())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TakhfifHajmiModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull TakhfifHajmiModel takhfifHajmiModel) {
                        Log.i("TakhfifHajmiModelRx", "onNext: "+takhfifHajmiModel.getCcTakhfifHajmi());
                        Log.i("TakhfifHajmiModelRx", "onNext: "+ccTakhfifHajmiList);
//                        if(!ccTakhfifHajmiList.contains(String.valueOf(takhfifHajmiModel.getCcTakhfifHajmi())));
                        ccTakhfifHajmiList.add(String.valueOf(takhfifHajmiModel.getCcTakhfifHajmi()));


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "  getCcTakhfifHajmisRx: " + e.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", "onComplete: ccTakhfifHajmi" + ccTakhfifHajmis);
                    }
                });
    }

    private void getccTakhfifSenfiRx(ArrayList<TakhfifSenfiModel> takhfifSenfiTitrModels) {
        Observable.fromIterable(takhfifSenfiTitrModels)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TakhfifSenfiModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull TakhfifSenfiModel takhfifSenfiModel) {
                        ccTakhfifSenfiList.add(String.valueOf(takhfifSenfiModel.getCcTakhfifSenfi()));



                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "  getCcTakhfifHajmisRx: onError" + e.getMessage());
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
//                        mPresenter.onFailedGetProgram(++webCounter, String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", +itemCounter + "  getCcTakhfifHajmisRx: onComplete");
                        Log.i("RxJavaRequest", "onComplete: ccTakhfifSenfi" + ccTakhfifSenfis);
//                        getAllTakhfifSatrRx(getProgramType);

                    }
                });
    }

    private void getAllTakhfifSatrRx(int getProgramType) {

        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        Log.i("RxJavaRequest", "getAllTakhfifSatrRx: "+ccTakhfifHajmis);

        ArrayList<String> ccTakhfifHajmiArrayList = new ArrayList<String>();
        ccTakhfifHajmiArrayList.addAll(ccTakhfifHajmiList);
        ccTakhfifHajmis = new CollectionUtils().convertStringArrayToString(ccTakhfifHajmiArrayList);

        ArrayList<String> ccTakhfifSenfiArrayList = new ArrayList<String>();
        ccTakhfifSenfiArrayList.addAll(ccTakhfifSenfiList);
        ccTakhfifSenfis = new CollectionUtils().convertStringArrayToString(ccTakhfifSenfiArrayList);

        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getTakhfifHajmiSatr("2", ccMarkazSazmanForosh, ccTakhfifHajmis)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllTakhfifSatrRx", "getTakhfifHajmiSatr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvTakhfifHajmiSatrResultResponse -> ++webCounter[0])

                ,
                apiServiceRxjava.getTakhfifSenfiSatr("2", ccMarkazSazmanForosh, ccTakhfifSenfis)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllTakhfifSatrRx", "getTakhfifSenfiSatr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllvTakhfifHajmiSatrResultResponse -> ++webCounter[0])

                , (getAllvTakhfifHajmiSatrResultResponse, getAllvTakhfifSenfiSatrResultResponse) -> {
                    updateTakhfifHajmiSatrTable(getProgramType, getAllvTakhfifHajmiSatrResultResponse.body().getData(), getAllvTakhfifSenfiSatrResultResponse.body().getData());
                    Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t getAllTakhfifSatrRx");

                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllTakhfifSatrRx) {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "  getAllTakhfifSatrRx:" + e.getMessage());

                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "  getAllTakhfifSatrRx: onComplete");
                    }
                });
    }

    private void updateTakhfifHajmiTable(int getProgramType
            , GetAllvTakhfifHajmiByccMarkazForoshResult getAllvTakhfifHajmiByccMarkazForoshResult
            , GetAllvTakhfifNaghdyByccMarkazForoshResult getAllvTakhfifNaghdyByccMarkazForoshResult
            , GetAllvTakhfifSenfiByccMarkazForoshResult getAllvTakhfifSenfiByccMarkazForoshResult) {
        if (getAllvTakhfifHajmiByccMarkazForoshResult != null) {
            TakhfifHajmiRepository takhfifHajmiRepository = new TakhfifHajmiRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = takhfifHajmiRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {

                            Disposable insertGroupDisposable = takhfifHajmiRepository.insertGroup(getAllvTakhfifHajmiByccMarkazForoshResult.getData())
                                    .subscribe(insertGroup -> {


                                        if (insertGroup) {
                                            //تخفیف حجمی
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTakhfifHajmiTable:");
                                            updateTakhfifNaghdiTable(getProgramType
                                                    , getAllvTakhfifNaghdyByccMarkazForoshResult
                                                    , getAllvTakhfifSenfiByccMarkazForoshResult);

                                        } else {
                                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                            throwException("updateTakhfifHajmiTable");
                                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiTable:error");

                                        }
                                    }, throwable -> {
                                        throwException("updateTakhfifHajmiTable");
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiTable:" + throwable.getMessage());

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            throwException("updateTakhfifHajmiTable");
                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiTable:error");
                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTakhfifHajmiTable:");
            updateTakhfifNaghdiTable(getProgramType
                    , getAllvTakhfifNaghdyByccMarkazForoshResult
                    , getAllvTakhfifSenfiByccMarkazForoshResult);
        }
    }


    private void updateTakhfifHajmiSatrTable(int getProgramType, ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels
            , ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels) {
        TakhfifHajmiSatrRepository takhfifHajmiSatrRepository = new TakhfifHajmiSatrRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = takhfifHajmiSatrRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {

                    if (deleteAll) {
                        Disposable insertGroupDisposable = takhfifHajmiSatrRepository.insertGroup(takhfifHajmiSatrModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        //اقلام تخفیف حجمی
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiSatrTable:");
                                        updateTakhfifSenfiSatrTable(getProgramType, takhfifSenfiSatrModels);

//                                                updateTakhfifNaghdiTable(getProgramType);
                                    } else {
                                        throwException("updateTakhfifHajmiSatrTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiSatrTable:" + throwable.getMessage());
                                    throwException("updateTakhfifSenfiSatrTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifHajmiSatrTable:error");

                        throwException("updateTakhfifSenfiSatrTable");

                    }
                });
        compositeDisposable.addAll(deleteAllDisposable);
    }

    private void updateTakhfifNaghdiTable(int getProgramType
            , GetAllvTakhfifNaghdyByccMarkazForoshResult getAllvTakhfifNaghdyByccMarkazForoshResult
            , GetAllvTakhfifSenfiByccMarkazForoshResult getAllvTakhfifSenfiByccMarkazForoshResult) {
        if (getAllvTakhfifNaghdyByccMarkazForoshResult != null) {
            TakhfifNaghdyRepository takhfifNaghdyRepository = new TakhfifNaghdyRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = takhfifNaghdyRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = takhfifNaghdyRepository.insertGroup(getAllvTakhfifNaghdyByccMarkazForoshResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            //تخفیف نقدی
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateTakhfifNaghdiTable:");
                                            updateTakhfifSenfiTable(getProgramType, getAllvTakhfifSenfiByccMarkazForoshResult);

                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifNaghdiTable: error");
                                            throwException("updateTakhfifNaghdiTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifNaghdiTable:" + throwable.getMessage());
                                        throwException("updateTakhfifNaghdiTable");
                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifNaghdiTable:error");
                            throwException("updateTakhfifNaghdiTable");
                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateTakhfifNaghdiTable:");
            updateTakhfifSenfiTable(getProgramType, getAllvTakhfifSenfiByccMarkazForoshResult);
        }
    }

    private void updateTakhfifSenfiTable(int getProgramType, GetAllvTakhfifSenfiByccMarkazForoshResult getAllvTakhfifSenfiByccMarkazForoshResult) {

        if (getAllvTakhfifSenfiByccMarkazForoshResult != null) {
            TakhfifSenfiRepository takhfifSenfiRepository = new TakhfifSenfiRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = takhfifSenfiRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = takhfifSenfiRepository.insertGroup(getAllvTakhfifSenfiByccMarkazForoshResult.getData())
                                    .subscribe(insertGroup -> {
                                        if (insertGroup) {
                                            //تخفیف صنفی
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTakhfifSenfiTable");
                                            getAllTakhfifSatrRx(getProgramType);
//                                                updateTakhfifSenfiSatrTable(getProgramType);


                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifSenfiTable:error");
                                            throwException("updateTakhfifSenfiTable");

                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifSenfiTable:" + throwable.getMessage());
                                        throwException("updateTakhfifSenfiTable");

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifSenfiTable:error");

                            throwException("updateTakhfifSenfiTable");

                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTakhfifSenfiTable");
            getAllTakhfifSatrRx(getProgramType);
        }
    }

    private void updateTakhfifSenfiSatrTable(int getProgramType, ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels) {
        TakhfifSenfiSatrRepository takhfifSenfiSatrRepository = new TakhfifSenfiSatrRepository(mPresenter.getAppContext());

        Disposable deleteAllDisposable = takhfifSenfiSatrRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {

                        Disposable insertGroupDisposable = takhfifSenfiSatrRepository.insertGroup(takhfifSenfiSatrModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {


                                        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                            //اقلام تخفیف صنفی
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateTakhfifSenfiSatrTable");
                                            getTaghvimTatilRx(getProgramType);
                                        } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                            getRptKalaInfo(getProgramType);
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t getRptKalaInfo");
                                        } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                                            updateJayezehTakhfifVersion();
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateJayezehTakhfifVersion");
                                        }

                                    } else {
                                        throwException("updateTakhfifSenfiSatrTable");
                                    }
                                }, throwable -> {
                                    throwException("updateTakhfifSenfiSatrTable");
                                    Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifSenfiSatrTable:" + throwable.getMessage());


                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateTakhfifSenfiSatrTable:error");
                        throwException("updateTakhfifSenfiSatrTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    //new
    private void getTakhfifHajmi(final int getProgramType) {
        final TakhfifHajmiDAO takhfifHajmiDAO = new TakhfifHajmiDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        Log.d("takhfifHajmi", "ccMarkazSazmanForosh : " + ccMarkazSazmanForosh);
        takhfifHajmiDAO.fetchTakhfifHajmiTitr(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, "-1", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifHajmiDAO.deleteAll();
                        ccTakhfifHajmis = takhfifHajmiDAO.insertGroup(arrayListData);
                        if (deleteResult && ccTakhfifHajmis != null && !ccTakhfifHajmis.trim().equals("")) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifHajmiSatr(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    //new
    private void getTakhfifHajmiSatr(final int getProgramType) {
        final TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(mPresenter.getAppContext());
        takhfifHajmiSatrDAO.fetchTakhfifHajmiSatr(mPresenter.getAppContext(), activityNameForLog, "-1", ccTakhfifHajmis, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifHajmiSatrDAO.deleteAll();
                        boolean insertResult = takhfifHajmiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifNaghdi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    /*private void getTakhfifHajmiSatr(final int getProgramType)
    {
        final TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(mPresenter.getAppContext());
        takhfifHajmiSatrDAO.fetchTakhfifHajmiSatr(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = takhfifHajmiSatrDAO.deleteAll();
                        boolean insertResult = takhfifHajmiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getTakhfifNaghdi(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }*/


    private void getTakhfifNaghdi(final int getProgramType) {
        final TakhfifNaghdyDAO takhfifNaghdyDAO = new TakhfifNaghdyDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        takhfifNaghdyDAO.fetchTakhfifNaghdy(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifNaghdyDAO.deleteAll();
                        boolean insertResult = takhfifNaghdyDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifSenfi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTakhfifSenfi(final int getProgramType) {
        final TakhfifSenfiDAO takhfifSenfiDAO = new TakhfifSenfiDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        Log.i("ccMarkazSazmanForosh", "getTakhfifSenfi: " + ccMarkazSazmanForosh);
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        takhfifSenfiDAO.fetchTakhfifSenfi(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, "-1", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Log.i("takhfifSenfi", "onSuccess: " + arrayListData.size());
                ArrayList<TakhfifSenfiModel> takhfifSenfiModels = arrayListData;
                for (TakhfifSenfiModel takhfifSenfiModel : takhfifSenfiModels) {
                    Log.i("takhfifSenfiModel", "onSuccess: " + takhfifSenfiModel);
                }
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifSenfiDAO.deleteAll();
                        ccTakhfifSenfis = takhfifSenfiDAO.insertGroup(arrayListData);
                        Log.i("xxTakhfifSenfis", "run: " + ccTakhfifSenfis);
                        if (deleteResult && ccTakhfifSenfis != null && !ccTakhfifSenfis.trim().equals("")) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifSenfiSatr(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTakhfifSenfiSatr(final int getProgramType) {
        final TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(mPresenter.getAppContext());
        takhfifSenfiSatrDAO.fetchTakhfifSenfiSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForosh), ccTakhfifSenfis, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifSenfiSatrDAO.deleteAll();
                        boolean insertResult = takhfifSenfiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
//                                getTaghvimTatil(getProgramType, String.valueOf(ccMarkazAnbar));
                                getTaghvimTatilRx(getProgramType);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                getRptKalaInfo(getProgramType);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                                updateJayezehTakhfifVersion();
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void updateJayezehTakhfifVersion() {
        try {
            int serverVersion = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_UPDATE_JAYEZEH_TAKHFIF));
            Log.d("GetProgram", "updateJayezehTakhfifVersion : " + serverVersion);
            LocalConfigShared localConfigShared = new LocalConfigShared(mPresenter.getAppContext());
            localConfigShared.remove(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION);
            localConfigShared.putInt(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION, serverVersion);
            Log.d("GetProgram", "updateJayezehTakhfifVersion : " + serverVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private  void getTaghvimTatilRx(final int getProgramType)
    {
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getAllTaghvimTatilByccMarkaz(String.valueOf(ccMarkazAnbar))
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getTaghvimTatil", "getTaghvimTatil"))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetAllTaghvimTatilByccMarkazResult>>() {
                    @Override
                    public void onSubscribe( Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext( Response<GetAllTaghvimTatilByccMarkazResult> getAllTaghvimTatilByccMarkazResultResponse) {
                        webCounter[0]++;
                        ArrayList<TaghvimTatilModel> taghvimTatilModels = getAllTaghvimTatilByccMarkazResultResponse.body().getData();
                        updateTaghvimTatilTable(getProgramType, taghvimTatilModels);

                    }

                    @Override
                    public void onError( Throwable e)
                    {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });

    }

//    private void getTakhvimTatilAndCodeNoeVosolRx(final int getProgramType) {
//        ArrayList<TaghvimTatilModel> taghvimTatilModels = new ArrayList<>();
//        final int[] webCounter = {itemCounter};
//        apiServiceRxjava.getAllTaghvimTatilByccMarkaz(String.valueOf(ccMarkazAnbar))
//                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx", "getAllTaghvimTatilByccMarkaz"))
//                        .subscribeOn(Schedulers.io())
//                        .doOnNext(getAllTaghvimTatilByccMarkazResultResponse -> webCounter[0]++)
//                , apiServiceRxjava.getCodeNoeVosol()
//                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getNoeHesabNoeMalekiatTedadFaktorMoshtaryRx", "getTakhvimTatilAndCodeNoeVosolRx"))
//                        .subscribeOn(Schedulers.io())
//                        .doOnNext(getCodeNoeVosolResultResponse -> webCounter[0]++)
//                , (getAllTaghvimTatilByccMarkazResultResponse, getCodeNoeVosolResultResponse) ->
//                {
//
//                    taghvimTatilModels.addAll(getAllTaghvimTatilByccMarkazResultResponse.body().getData());
//                    codeNoeVosolModels.addAll(getCodeNoeVosolResultResponse.body().getData());
//                    return true;
//
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Boolean>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        compositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Boolean getTakhvimTatilAndCodeNoeVosol) {
//                        updateTaghvimTatilTable(getProgramType, taghvimTatilModels);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "getTakhvimTatilAndCodeNoeVosol onError: " + e.getMessage());
//                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }

    private void updateTaghvimTatilTable(int getProgramType, ArrayList<TaghvimTatilModel> taghvimTatilModels) {
        TaghvimTatilRepository taghvimTatilRepository = new TaghvimTatilRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = taghvimTatilRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {

                        Disposable insertGroupDisposable = taghvimTatilRepository.insertGroup(taghvimTatilModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTaghvimTatilTable");
                                        //codeNoeVosol
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + " \t updateCodeNoeVosolTable");
                                        deleteKardexAndKardexSatrTable(getProgramType);

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateTaghvimTatilTable:" + throwable.getMessage());
                                    throwException("updateTaghvimTatilTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateTaghvimTatilTable:error");
                        throwException("updateTaghvimTatilTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

//    private void updateCodeNoeVosolTable(int getProgramType, ArrayList<CodeNoeVosolModel> codeNoeVosolModels) {
//        CodeNoeVosolRepository codeNoeVosolRepository = new CodeNoeVosolRepository(mPresenter.getAppContext());
//        Disposable deleteAllDisposable = codeNoeVosolRepository.deleteAll()
//                .subscribeOn(Schedulers.io())
//                .subscribe(deleteAll -> {
//                    if (deleteAll) {
//
//                        Disposable insertGroupDisposable = codeNoeVosolRepository.insertGroup(codeNoeVosolModels)
//                                .subscribe(insertGroup -> {
//                                    if (insertGroup) {
//                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + " \t updateCodeNoeVosolTable");
//                                        deleteKardexAndKardexSatrTable(getProgramType);
//                                    } else {
//
//                                        throwException("updateCodeNoeVosolTable");
//                                    }
//                                }, throwable -> {
//                                    Log.i("RxJavaRequest", +itemCounter + "  updateCodeNoeVosolTable:" + throwable.getMessage());
//                                    throwException("updateCodeNoeVosolTable");
//
//                                });
//                        compositeDisposable.add(insertGroupDisposable);
//
//                    } else {
//                        Log.i("RxJavaRequest", +itemCounter + "  updateCodeNoeVosolTable:error");
//                        throwException("updateCodeNoeVosolTable");
//
//                    }
//                });
//        compositeDisposable.add(deleteAllDisposable);
//    }

    private void deleteKardexAndKardexSatrTable(int getProgramType) {
        KardexRepository kardexRepository = new KardexRepository(mPresenter.getAppContext());
        KardexSatrRepository kardexSatrRepository = new KardexSatrRepository(mPresenter.getAppContext());
        Disposable disposableDeleteKardex = kardexRepository.deleteAll()
                .subscribe(deleteAll -> {

                    if (deleteAll) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

                        Disposable disposableDeleteKardexSatr = kardexSatrRepository.deleteAll()
                                .subscribe(deleteAll1 -> {
                                    if (deleteAll1) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t  deleteKardexAndKardexSatrTable");
                                        deleteMarjoeeKamelImageRx(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  deleteKardexAndKardexSatrTable:error");
                                        throwException("deleteKardexAndKardexSatrTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  deleteKardexAndKardexSatrTable:" + throwable.getMessage());
                                    throwException("deleteKardexAndKardexSatrTable");

                                });
                        compositeDisposable.add(disposableDeleteKardexSatr);
                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  deleteKardexAndKardexSatrTable:error");
                        throwException("deleteKardexAndKardexSatrTable");

                    }

                }, throwable -> {
                    Log.i("RxJavaRequest", +itemCounter + "  deleteKardexAndKardexSatrTable:" + throwable.getMessage());

                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                });
        compositeDisposable.add(disposableDeleteKardex);

    }

    private void deleteMarjoeeKamelImageRx(int getProgramType) {
        MarjoeeKamelImageRepository marjoeeKamelImageRepository = new MarjoeeKamelImageRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = marjoeeKamelImageRepository.deleteAll()
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  deleteMarjoeeKamelImageRx");

                        getAllRptApis(getProgramType);


                    } else {
                        throwException("deleteMarjoeeKamelImageRx");
                    }

                }, throwable -> throwException("deleteMarjoeeKamelImageRx"));
        compositeDisposable.add(disposableDeleteAll);
    }

    private void getTaghvimTatil(final int getProgramType, String ccMarkazAnbar) {
        final TaghvimTatilDAO taghvimTatilDAO = new TaghvimTatilDAO(mPresenter.getAppContext());
        taghvimTatilDAO.fetchTaghvimTatil(mPresenter.getAppContext(), activityNameForLog, ccMarkazAnbar, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = taghvimTatilDAO.deleteAll();
                        boolean insertResult = taghvimTatilDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getCodeNoeVosol(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getCodeNoeVosol(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        deleteKardex(getProgramType);

//        final CodeNoeVosolDAO codeNoeVosolDAO = new CodeNoeVosolDAO(mPresenter.getAppContext());
//        codeNoeVosolDAO.fetchCodeNoeVosol(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
//            @Override
//            public void onSuccess(final ArrayList arrayListData) {
//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        boolean deleteResult = codeNoeVosolDAO.deleteAll();
//                        boolean insertResult = codeNoeVosolDAO.insertGroup(arrayListData);
//                        if (deleteResult && insertResult) {
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            deleteKardex(getProgramType);
//                        } else {
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
//                        }
//                    }
//                };
//                thread.start();
//            }

//            @Override
//            public void onFailed(String type, String error) {
//                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
//            }
//        });
    }


    private void deleteKardex(final int getProgramType) {
        final KardexDAO kardexDAO = new KardexDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = kardexDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteKardexSatr(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void deleteKardexSatr(final int getProgramType) {
        final KardexSatrDAO kardexSatrDAO = new KardexSatrDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = kardexSatrDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteMarjoeeKamelImage(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void deleteMarjoeeKamelImage(final int getProgramType) {
        final MarjoeeKamelImageDAO marjoeeKamelImageDAO = new MarjoeeKamelImageDAO(mPresenter.getAppContext());
        final MarjoeeKamelImageTmpDAO marjoeeKamelImageTmpDAO = new MarjoeeKamelImageTmpDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = marjoeeKamelImageDAO.deleteAll();
                boolean deleteTempResult = marjoeeKamelImageTmpDAO.deleteAll();
                if (deleteResult && deleteTempResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getRptMojodyAnbar(getProgramType, anbarakAfrad);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void getAllRptApis(final int getProgramType) {
        //گزارش موجودی انبار
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش موجودی انبار:");

        //گزارش کالا
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش کالا");

        //گزارش فاکتورهای توزیع نشده
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش فاکتورهای توزیع نشده:");

        //گزارش ویزیت فروشنده
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش ویزیت فروشنده:");

        //تبلیغات
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  تبلیغات:");

        //تنظیمات سیستم
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  تنظیمات سیستم:");

        //گزارش لیست وصول
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش لیست وصول:");

        //گزارش خرید کالا
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش خرید کالا:");

        //گزارش مسیر فروشنده
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "  گزارش مسیر فروشنده:");


        if (noeMasouliat == 4 || noeMasouliat == 5) {
            ccForoshandeh = 0;
        } else {
            ccMamorPakhsh = 0;
        }
        String ccForoshandehAsnad;
        final int[] webCounter = {itemCounter};
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            ccForoshandehAsnad = String.valueOf(ccForoshandeh);
        } else {
            ccForoshandehAsnad = ccForoshandehString;
        }
        String version = new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
        Observable.zip(apiServiceRxjava.getAllrptDarkhastFaktorHavalehVazeiat(String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh)).subscribeOn(Schedulers.io()).doOnNext(getAllTaghiratVersionPPCResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllTaghiratVersionPPC("1", version)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllrptDarkhastFaktorHavalehVazeiat"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllTaghiratVersionPPCResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllMoshtaryChidman(ccMasirs)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllMoshtaryChidman"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllMoshtaryChidmanResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllMoshtaryForoshandeh(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllMoshtaryForoshandeh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllMoshtaryForoshandehResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllrptAmarForosh(String.valueOf(ccForoshandeh), selectedDateGregorian)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllrptAmarForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllrptAmarForoshResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllrptListMoavaghForoshandeh(String.valueOf(ccAfrad))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllrptListMoavaghForoshandeh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllrptListMoavaghForoshandehResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllRptSanad(ccForoshandehAsnad)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllRptSanad"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllrptListAsnadForoshandehResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getAllRptHadafForosh(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllRptApis", "getAllRptHadafForosh"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllrptHadafeForoshResponseResponse -> webCounter[0]++)
                , (getAllrptDarkhastFaktorHavalehVazeiatResultResponse, getVersionResultResponse, getAllMoshtaryChidmanResultResponse, getAllMoshtaryForoshandehResultResponse, getAllrptAmarForoshResultResponse, getAllrptListMoavaghForoshandehResultResponse, getAllrptListAsnadForoshandehResultResponse, getAllrptHadafeForoshResponse) -> {


                    updateRptApiTables(getProgramType,
                            getAllrptDarkhastFaktorHavalehVazeiatResultResponse.body().getData()
                            , getVersionResultResponse.body().getData()
                            , getAllMoshtaryChidmanResultResponse.body().getData()
                            , getAllMoshtaryForoshandehResultResponse.body().getData()
                            , getAllrptAmarForoshResultResponse.body().getData()
                            , getAllrptListMoavaghForoshandehResultResponse.body().getData()
                            , getAllrptListAsnadForoshandehResultResponse.body().getData()
                            , getAllrptHadafeForoshResponse.body().getrptHadafForoshModels());


                    return true;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("RxJavaRequest", +itemCounter + "webCounter" + webCounter[0] + "getAllRptApis: time" + System.currentTimeMillis());

                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getAllRptApis) {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updateRptApiTables(int getProgramType
            , ArrayList<RptDarkhastFaktorVazeiatPPCModel> rptDarkhastFaktorVazeiatPPCModels,
                                    ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels,
                                    ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels,
                                    ArrayList<AllMoshtaryForoshandehModel> moshtaryForoshandehModels
            , ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        updateDarkhastFaktorVaziatTable(getProgramType,
                rptDarkhastFaktorVazeiatPPCModels
                , taghiratVersionPPCModels
                , moshtaryChidmanModels
                , moshtaryForoshandehModels
                , rptForoshModels
                , mandehdarModels
                , rptSanadModels
                , rptHadafForoshModels);
    }


    private void updateDarkhastFaktorVaziatTable(int getProgramType
            , ArrayList<RptDarkhastFaktorVazeiatPPCModel> rptDarkhastFaktorVazeiatPPCModels,
                                                 ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels,
                                                 ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels,
                                                 ArrayList<AllMoshtaryForoshandehModel> moshtaryForoshandehModels
            , ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModel) {
        RptDarkhastFaktorVazeiatPPCRepository rptDarkhastFaktorVazeiatPPCRepository = new RptDarkhastFaktorVazeiatPPCRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = rptDarkhastFaktorVazeiatPPCRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = rptDarkhastFaktorVazeiatPPCRepository.insertGroup(rptDarkhastFaktorVazeiatPPCModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t rptDarkhastFaktorVazeiatPPCRepository:");
                                        updateTaghiratVersionPpcTable(getProgramType
                                                , taghiratVersionPPCModels
                                                , moshtaryChidmanModels
                                                , moshtaryForoshandehModels
                                                , rptForoshModels
                                                , mandehdarModels
                                                , rptSanadModels
                                                , rptHadafForoshModel);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  rptDarkhastFaktorVazeiatPPCRepository:error");
                                        throwException("updateDarkhastFaktorVaziatTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  rptDarkhastFaktorVazeiatPPCRepository:" + throwable.getMessage());
                                    throwException("updateDarkhastFaktorVaziatTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  rptDarkhastFaktorVazeiatPPCRepository:error");

                        throwException("updateDarkhastFaktorVaziatTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    private void updateTaghiratVersionPpcTable(int getProgramType, ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels,
                                               ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels,
                                               ArrayList<AllMoshtaryForoshandehModel> moshtaryForoshandehModels
            , ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModel) {
        TaghiratVersionPPCRepository taghiratVersionPPCRepository = new TaghiratVersionPPCRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = taghiratVersionPPCRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = taghiratVersionPPCRepository.insertGroup(taghiratVersionPPCModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + " \t updateTaghiratVersionPpcTable:");
                                        updateMoshtaryChidemanTable(getProgramType
                                                , moshtaryChidmanModels
                                                , moshtaryForoshandehModels
                                                , rptForoshModels
                                                , mandehdarModels
                                                , rptSanadModels
                                                , rptHadafForoshModel);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTaghiratVersionPpcTable:error");
                                        throwException("updateTaghiratVersionPpcTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateTaghiratVersionPpcTable:" + throwable.getMessage());
                                    throwException("updateTaghiratVersionPpcTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateTaghiratVersionPpcTable:error");

                        throwException("updateTaghiratVersionPpcTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMoshtaryChidemanTable(int getProgramType, ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels
            , ArrayList<AllMoshtaryForoshandehModel> moshtaryForoshandehModels
            , ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModel) {
        MoshtaryChidmanRepository moshtaryChidmanRepository = new MoshtaryChidmanRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryChidmanRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryChidmanRepository.insertGroup(moshtaryChidmanModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMoshtaryChidemanTable");
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        updateMoshtaryForoshandehTables(getProgramType, moshtaryForoshandehModels
                                                , rptForoshModels
                                                , mandehdarModels
                                                , rptSanadModels
                                                , rptHadafForoshModel);
                                    } else {

                                        throwException("updateMoshtaryChidemanTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryChidemanTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryChidemanTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryChidemanTable:error");

                        throwException("updateMoshtaryChidemanTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMoshtaryForoshandehTables(int getProgramType, ArrayList<AllMoshtaryForoshandehModel> moshtaryForoshandehModels
            , ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModel) {
        AllMoshtaryForoshandehRepository allMoshtaryForoshandehRepository = new AllMoshtaryForoshandehRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = allMoshtaryForoshandehRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = allMoshtaryForoshandehRepository.insertGroup(moshtaryForoshandehModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter: " + itemCounter + "\t  updateMoshtaryForoshandehModels:");
                                        updateRptAmarForoshTable(getProgramType
                                                , rptForoshModels
                                                , mandehdarModels
                                                , rptSanadModels
                                                , rptHadafForoshModel);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryForoshandehModels:");
                                        throwException("updateMoshtaryForoshandehTables");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryForoshandehModels:" + throwable.getMessage());
                                    throwException("updateMoshtaryForoshandehTables");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryForoshandehModels:error");

                        throwException("updateMoshtaryForoshandehTables");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateRptAmarForoshTable(int getProgramType, ArrayList<RptForoshModel> rptForoshModels
            , ArrayList<RptMandehdarModel> mandehdarModels
            , ArrayList<RptSanadModel> rptSanadModels
            , ArrayList<RptHadafForoshModel> rptHadafForoshModel) {
        RptForoshRepository rptForoshRepository = new RptForoshRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = rptForoshRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = rptForoshRepository.insertGroup(rptForoshModels)
                                .subscribe(insertGroup -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  rptAmarForoshModels:" + insertGroup);

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

//

                                        updateRptMandehDarTable(getProgramType
                                                , mandehdarModels
                                                , rptSanadModels
                                                , rptHadafForoshModel);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  rptAmarForoshModels:error");
                                        throwException("updateRptAmarForoshTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  rptAmarForoshModels:" + throwable.getMessage());
                                    throwException("updateRptAmarForoshTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  rptAmarForoshModels:error");

                        throwException("updateRptAmarForoshTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateRptMandehDarTable(
            int getProgramType,
            ArrayList<RptMandehdarModel> rptMandehDarModels,
            ArrayList<RptSanadModel> rptSanadModels,
            ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        RptMandehdarRepository rptMandehdarRepository = new RptMandehdarRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = rptMandehdarRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = rptMandehdarRepository.insertGroup(rptMandehDarModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  rptMandehdarRepository:");
                                        updateRptSanadTable(getProgramType
                                                , rptSanadModels
                                                , rptHadafForoshModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  rptMandehdarRepository:error");
                                        throwException("updateRptMandehDarTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  rptMandehdarRepository:" + throwable.getMessage());
                                    throwException("updateRptMandehDarTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  rptMandehdarRepository:error");

                        throwException("updateRptMandehDarTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateRptSanadTable(int getProgramType,
                                     ArrayList<RptSanadModel> rptSanadModels,
                                     ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        RptSanadRepository rptSanadRepository = new RptSanadRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = rptSanadRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = rptSanadRepository.insertGroup(rptSanadModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateRptSanadTable");
                                        updateRptHadafForoshTable(getProgramType
                                                , rptHadafForoshModels);
//                                                updateMoshtaryForoshandehModels(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateRptSanadTable:error");
                                        throwException("updateRptSanadTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateRptSanadTable:" + throwable.getMessage());
                                    throwException("updateRptSanadTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateRptSanadTable:error");

                        throwException("updateRptSanadTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateRptHadafForoshTable(int getProgramType, ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {

            RptHadafForoshRepository rptHadafForoshRepository = new RptHadafForoshRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = rptHadafForoshRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = rptHadafForoshRepository.insertGroup(rptHadafForoshModels)
                                    .subscribe(insertGroup -> {


                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + " \t updateRptHadafForoshTable:");
                                            if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
                                                getMarjoeeApisRx(getProgramType);
                                            } else {
                                                //اعلام مرجوعی
                                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                                Log.i("RxJavaRequest", "itemCounter" + itemCounter + "اعلام مرجوعی");
                                                //اعلام مرجوعی سطر
                                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                                Log.i("RxJavaRequest", "itemCounter" + itemCounter + "اعلام مرجوعی سطر");

                                                deleteElamMarjoeeTedadRx(getProgramType);
                                            }
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateRptHadafForoshTable:error");
                                            throwException("updateRptHadafForoshTable");

                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateRptHadafForoshTable:" + throwable.getMessage());
                                        throwException("updateRptHadafForoshTable");

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateRptHadafForoshTable:error");

                            throwException("updateRptHadafForoshTable");

                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {

            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "itemCounter:" + itemCounter + " \t updateRptHadafForoshTable:");
            if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
                getMarjoeeApisRx(getProgramType);
            } else {
                //اعلام مرجوعی
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                Log.i("RxJavaRequest", "itemCounter" + itemCounter + "اعلام مرجوعی");
                //اعلام مرجوعی سطر
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                Log.i("RxJavaRequest", "itemCounter" + itemCounter + "اعلام مرجوعی سطر");

                deleteElamMarjoeeTedadRx(getProgramType);
            }
        }
    }


    private void getRptMojodyAnbar(final int getProgramType, String ccAnbarakAfrad) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptFaktorVazeiat(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));

        /*final RptMojodiAnbarDAO rptMojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        rptMojodiAnbarDAO.fetchRptMojodyAnbarak(mPresenter.getAppContext(), activityNameForLog, ccAnbarakAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptMojodiAnbarDAO.deleteAll();
                        boolean insertResult = rptMojodiAnbarDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptFaktorVazeiat(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptFaktorVazeiat(final int getProgramType, String ccForoshandeh, String ccMamorPakhsh) {
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            ccForoshandeh = "0";
        } else {
            ccMamorPakhsh = "0";
        }

        final RptDarkhastFaktorVazeiatPPCDAO rptDarkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(mPresenter.getAppContext());
        rptDarkhastFaktorVazeiatPPCDAO.fetchRptDarkhastFaktorVazeiat(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMamorPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptDarkhastFaktorVazeiatPPCDAO.deleteAll();
                        boolean insertResult = rptDarkhastFaktorVazeiatPPCDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptKalaInfo(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    //TODO delete
    private void getRptKalaInfo(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
            getRptFaktorTozieNashode(getProgramType, String.valueOf(ccForoshandeh));
        }

        /*final RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(mPresenter.getAppContext());
        rptKalaInfoDAO.fetchRptKalaInfo(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptKalaInfoDAO.deleteAll();
                        boolean insertResult = rptKalaInfoDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_FULL())
                            {
                                getRptFaktorTozieNashode(getProgramType , String.valueOf(ccForoshandeh));
                            }
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }

    //TODO delete
    private void getRptFaktorTozieNashode(final int getProgramType, final String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptForoshandehVisit(getProgramType, ccForoshandeh);

        /*final RptFaktorTozieNashodehDAO rptFaktorTozieNashodehDAO = new RptFaktorTozieNashodehDAO(mPresenter.getAppContext());
        rptFaktorTozieNashodehDAO.fetchAllrptFaktorTozieNashodeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptFaktorTozieNashodehDAO.deleteAll();
                        boolean insertResult = rptFaktorTozieNashodehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptForoshandehVisit(getProgramType , ccForoshandeh);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptForoshandehVisit(final int getProgramType, String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptVersionInfo(getProgramType);

        /*final RptVisitForoshandehForoshandehDAO rptVisitForoshandehForoshandehDAO = new RptVisitForoshandehForoshandehDAO(mPresenter.getAppContext());
        final RptVisitForoshandehMoshtaryDAO rptVisitForoshandehMoshtaryDAO = new RptVisitForoshandehMoshtaryDAO(mPresenter.getAppContext());
        rptVisitForoshandehMoshtaryDAO.fetchAllrptVisitForoshandehMoshtary(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteForoshandehResult = rptVisitForoshandehForoshandehDAO.deleteAll();
                        boolean deleteResult = rptVisitForoshandehMoshtaryDAO.deleteAll();
                        boolean insertResult = rptVisitForoshandehMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteForoshandehResult && deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptVersionInfo(getProgramType , "1");
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptVersionInfo(final int getProgramType) {
        String version = new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
        final TaghiratVersionPPCDAO taghiratVersionPPCDAO = new TaghiratVersionPPCDAO(mPresenter.getAppContext());
        taghiratVersionPPCDAO.fetchTaghiratVersionPPC(mPresenter.getAppContext(), activityNameForLog, "1", version, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = taghiratVersionPPCDAO.deleteAll();
                        boolean insertResult = taghiratVersionPPCDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTizerTablighat(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTizerTablighat(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMoshtaryChidman(getProgramType, ccMasirs);
     /*   final TizerTablighatPegahDAO tizerTablighatPegahDAO = new TizerTablighatPegahDAO(mPresenter.getAppContext());
        tizerTablighatPegahDAO.fetchTizerTablighat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = tizerTablighatPegahDAO.deleteAll();
                        boolean insertResult = tizerTablighatPegahDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryChidman(getProgramType , ccMasirs);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        }); */
    }


    private void getMoshtaryChidman(final int getProgramType, String ccMasirs) {
        final MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        moshtaryChidmanDAO.fetchMoshtaryChidman(mPresenter.getAppContext(), activityNameForLog, ccMasirs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryChidmanDAO.deleteAll();
                        boolean insertResult = moshtaryChidmanDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptMandehDar(getProgramType, String.valueOf(ccAfrad));
                            } else {
                                getSystemConfigTablet(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getSystemConfigTablet(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getAllMoshtaryForoshandeh(getProgramType, String.valueOf(ccForoshandeh));
    }


    private void getAllMoshtaryForoshandeh(final int getProgramType, final String ccForoshandeh) {
        final AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        allMoshtaryForoshandehDAO.fetchAllMoshtaryforoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = allMoshtaryForoshandehDAO.deleteAll();
                        boolean insertResult = allMoshtaryForoshandehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptForosh(getProgramType, ccForoshandeh, selectedDateGregorian);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(+itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getRptForosh(final int getProgramType, String ccForoshandeh, String currentDate) {
        final RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
        rptForoshDAO.fetchAllrptAmarForosh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, currentDate, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptForoshDAO.deleteAll();
                        boolean insertResult = rptForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptMandehDar(getProgramType, String.valueOf(ccAfrad));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getRptMandehDar(final int getProgramType, final String ccAfrad) {
        final RptMandehdarDAO mandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
        mandehdarDAO.fetchAllMandehdar(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mandehdarDAO.deleteAll();
                        boolean insertResult = mandehdarDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptAsnad(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getRptAsnad(final int getProgramType) {
        String ccForoshandehAsnad;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            ccForoshandehAsnad = String.valueOf(ccForoshandeh);
        } else {
            ccForoshandehAsnad = ccForoshandehString;
        }
        final RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
        rptSanadDAO.fetchAllRptSanad(mPresenter.getAppContext(), activityNameForLog, ccForoshandehAsnad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptSanadDAO.deleteAll();
                        boolean insertResult = rptSanadDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptHadaf(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getRptHadaf(final int getProgramType) {
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            final RptHadafForoshDAO rptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
            rptHadafForoshDAO.fetchAllrpHadafeForosh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = rptHadafForoshDAO.deleteAll();
                            boolean insertResult = rptHadafForoshDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                    getRptListVosol(getProgramType, String.valueOf(ccAfrad));
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getRptListVosol(getProgramType, String.valueOf(ccAfrad));
        }
    }


    private void getRptListVosol(final int getProgramType, String ccAfrad) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptKharidKala(getProgramType, "2", "");

        /*final RptListVosolDAO rptListVosolDAO = new RptListVosolDAO(mPresenter.getAppContext());
        rptListVosolDAO.fetchRPTListVosol(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptListVosolDAO.deleteAll();
                        boolean insertResult = rptListVosolDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptKharidKala(getProgramType , "2" , "");
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptKharidKala(final int getProgramType, String level, String ccKalaGoroh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        deleteRptMarjoee(getProgramType);

        /*final RptKharidKalaDAO rptKharidKalaDAO = new RptKharidKalaDAO(mPresenter.getAppContext());
        String foroshandeh = "";
        if(noeMasouliat == 5)
        {
            foroshandeh = ccForoshandehString;
        }
        else
        {
            foroshandeh = String.valueOf(ccForoshandeh);
        }
        rptKharidKalaDAO.fetchKharidKalaByccForoshandeh(mPresenter.getAppContext(), activityNameForLog, level, foroshandeh, ccKalaGoroh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptKharidKalaDAO.deleteAll();
                        boolean insertResult = rptKharidKalaDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            deleteRptMarjoee(getProgramType);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void deleteRptMarjoee(int getProgramType) {
        RptMarjoeeDAO rptMarjoeeDAO = new RptMarjoeeDAO(mPresenter.getAppContext());
        rptMarjoeeDAO.deleteAll();
        getRptMasir(getProgramType, String.valueOf(ccForoshandeh));
    }

    private void getRptMasir(final int getProgramType, final String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getElamMarjoee(getProgramType);

        /*final RptMasirDAO rptMasirDAO = new RptMasirDAO(mPresenter.getAppContext());
        rptMasirDAO.fetchRPTMasirForoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptMasirDAO.deleteAll();
                        boolean insertResult = rptMasirDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            //sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryEtebarPishfarz(getProgramType);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++webCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }

    private void getMarjoeeApisRx(int getProgramType) {
        final GetMarjoeeForoshandehByDarkhastFaktorTitrResult[] getMarjoeeForoshandehByDarkhastFaktorTitrResult = new GetMarjoeeForoshandehByDarkhastFaktorTitrResult[1];
        final GetMarjoeeForoshandehByDarkhastFaktorSatrResult[] getMarjoeeForoshandehByDarkhastFaktorSatrResult = new GetMarjoeeForoshandehByDarkhastFaktorSatrResult[1];
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getMarjoeeForoshandehByDarkhastFaktorTitr("1", ccDarkhastFaktorPakhsh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMarjoeeApisRx", "getMarjoeeForoshandehByDarkhastFaktorTitr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMarjoeeForoshandehByDarkhastFaktorTitrResultResponse -> webCounter[0]++)
                , apiServiceRxjava.getMarjoeeForoshandehByDarkhastFaktorSatr("2", ccDarkhastFaktorPakhsh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getMarjoeeApisRx", "getMarjoeeForoshandehByDarkhastFaktorSatr"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMarjoeeForoshandehByDarkhastFaktorSatrResultResponse -> webCounter[0]++)

                , (getMarjoeeForoshandehByDarkhastFaktorTitrResultResponse, getMarjoeeForoshandehByDarkhastFaktorSatrResultResponse) -> {
                    getMarjoeeForoshandehByDarkhastFaktorTitrResult[0] = getMarjoeeForoshandehByDarkhastFaktorTitrResultResponse.body();
                    getMarjoeeForoshandehByDarkhastFaktorSatrResult[0] = getMarjoeeForoshandehByDarkhastFaktorSatrResultResponse.body();


                    return true;


                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getMarjoeeApisRx) {
                        updateMarjoeeTitrTable(getProgramType, getMarjoeeForoshandehByDarkhastFaktorTitrResult[0], getMarjoeeForoshandehByDarkhastFaktorSatrResult[0]);


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void updateMarjoeeTitrTable(int getProgramType, GetMarjoeeForoshandehByDarkhastFaktorTitrResult getMarjoeeForoshandehByDarkhastFaktorTitrResult, GetMarjoeeForoshandehByDarkhastFaktorSatrResult getMarjoeeForoshandehByDarkhastFaktorSatrResult) {
        if (getMarjoeeForoshandehByDarkhastFaktorTitrResult != null) {
            ElamMarjoeePPCRepository elamMarjoeePPCRepository = new ElamMarjoeePPCRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = elamMarjoeePPCRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = elamMarjoeePPCRepository.insertGroup(getMarjoeeForoshandehByDarkhastFaktorTitrResult.getData())
                                    .subscribe(insertGroup -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeTitrTable:" + insertGroup);

                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            updateMarjoeeSatrTable(getProgramType, getMarjoeeForoshandehByDarkhastFaktorSatrResult);
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeTitrTable:error");
                                            throwException("updateMarjoeeTitrTable");

                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeTitrTable:" + throwable.getMessage());
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeTitrTable:error");

                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            updateMarjoeeSatrTable(getProgramType, getMarjoeeForoshandehByDarkhastFaktorSatrResult);
        }

    }

    private void updateMarjoeeSatrTable(int getProgramType, GetMarjoeeForoshandehByDarkhastFaktorSatrResult getMarjoeeForoshandehByDarkhastFaktorSatrResult) {
        if (getMarjoeeForoshandehByDarkhastFaktorSatrResult != null) {
            ElamMarjoeeSatrPPCRepository elamMarjoeeSatrPPCRepository = new ElamMarjoeeSatrPPCRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = elamMarjoeeSatrPPCRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = elamMarjoeeSatrPPCRepository.insertGroup(getMarjoeeForoshandehByDarkhastFaktorSatrResult.getData())
                                    .subscribe(insertGroup -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeSatrTable:" + insertGroup);

                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            deleteElamMarjoeeTedadRx(getProgramType);
                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeSatrTable:error");
                                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "updateMarjoeeSatrTable:" + throwable.getMessage());
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeSatrTable:error");

                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            deleteElamMarjoeeTedadRx(getProgramType);
        }


    }

    private void getElamMarjoee(final int getProgramType) {
        final ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            Log.d("getprogram", "fetch elam marjoee");
            elamMarjoeePPCDAO.fetchMarjoee(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktorPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = elamMarjoeePPCDAO.deleteAll();
                            boolean insertResult = elamMarjoeePPCDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getElamMarjoeeSatr(getProgramType);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Log.d("getprogram", "delete elam marjoee");
                    elamMarjoeePPCDAO.deleteAll();
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getElamMarjoeeSatr(getProgramType);
                }
            };
            thread.start();
        }
    }


    private void getElamMarjoeeSatr(final int getProgramType) {
        final ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            Log.d("getprogram", "fetch elam marjoee satr");
            elamMarjoeeSatrPPCDAO.fetchMarjoeeSatr(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktorPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = elamMarjoeeSatrPPCDAO.deleteAll();
                            boolean insertResult = elamMarjoeeSatrPPCDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                deleteElamMarjoeeTedad(getProgramType);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Log.d("getprogram", "delete elam marjoee satr");
                    elamMarjoeeSatrPPCDAO.deleteAll();
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteElamMarjoeeTedad(getProgramType);
                }
            };
            thread.start();
        }
    }

    private void deleteElamMarjoeeTedadRx(final int getProgramType) {
        ElamMarjoeeSatrPPCTedadRepository elamMarjoeeSatrPPCTedadRepository = new ElamMarjoeeSatrPPCTedadRepository(mPresenter.getAppContext());
        Disposable disposableDeleteAll = elamMarjoeeSatrPPCTedadRepository.deleteAll()
                .observeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {

                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        Log.d("getprogram", "itemCounter:" + itemCounter + " deleteElamMarjoeeTedadRx");
                        getDetailedApis(getProgramType);

                    } else {
                        throwException("deleteElamMarjoeeTedadRx");
                    }
                }, throwable -> throwException("deleteElamMarjoeeTedadRx"));
        compositeDisposable.add(disposableDeleteAll);


    }

    private void getDetailedApis(int getProgramType) {


        String allccMoshtary = ccMoshtarys;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            allccMoshtary = ccMoshtaryPakhsh;
        }
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getMoshtaryEtebarPishfarz(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getMoshtaryEtebarPishfarz"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMoshtaryEtebarPishfarzResultResponse -> ++webCounter[0])

                , apiServiceRxjava.getNoeFaaliatForMoarefiMoshtaryJadid(String.valueOf(ccForoshandeh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getNoeFaaliatForMoarefiMoshtaryJadid"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getNoeFaaliatForMoarefiMoshtaryJadidResultResponse -> ++webCounter[0])

                , apiServiceRxjava.getAllMoshtaryJadidDarkhastPPC()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getAllMoshtaryJadidDarkhastPPC"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllMoshtaryJadidDarkhastPPCResultResponse -> ++webCounter[0])

                , apiServiceRxjava.getAllGorohKalaNoeSenf()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getAllGorohKalaNoeSenf"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllGorohKalaNoeSenfResultResponse -> ++webCounter[0])

                , apiServiceRxjava.getAllMarkazShahrMarkazi()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getAllMarkazShahrMarkazi"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllMarkazShahrMarkaziResultResponse -> ++webCounter[0])

                , apiServiceRxjava.getAllNoeMoshtaryrialKharid(String.valueOf(ccMarkazSazmanForoshSakhtarForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getAllNoeMoshtaryrialKharid"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getAllNoeMoshtaryRialKharidResualtResponse -> ++webCounter[0])


                , apiServiceRxjava.getMahalCodePosti(String.valueOf(ccMarkazForosh))
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getMahalCodePosti"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getMahalCodePostiResultResponse -> ++webCounter[0])


                , apiServiceRxjava.getNoeVosolMoshtary(ccMarkazSazmanForosh)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getNoeVosolMoshtary"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(noeVosolMoshtaryResultResponse -> ++webCounter[0])


                , apiServiceRxjava.getAllMoshtaryBrand(allccMoshtary)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getDetailedApis", "getAllMoshtaryBrand"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(moshtaryBrandResultResponse -> ++webCounter[0])


                , (getMoshtaryEtebarPishfarzResultResponse, getNoeFaaliatForMoarefiMoshtaryJadidResultResponse, getAllMoshtaryJadidDarkhastPPCResultResponse, getAllGorohKalaNoeSenfResultResponse, getAllMarkazShahrMarkaziResultResponse, getAllNoeMoshtaryRialKharidResualtResponse, getMahalCodePostiResultResponse, noeVosolMoshtaryResultResponse, allMoshtaryBrandResultResponse) -> {
                    updateDetailApiTables(getProgramType, getMoshtaryEtebarPishfarzResultResponse.body().getData()
                            , getNoeFaaliatForMoarefiMoshtaryJadidResultResponse.body().getData()
                            , getAllMoshtaryJadidDarkhastPPCResultResponse.body().getData()
                            , getAllGorohKalaNoeSenfResultResponse.body().getData()
                            , getAllMarkazShahrMarkaziResultResponse.body().getData()
                            , getAllNoeMoshtaryRialKharidResualtResponse.body().getData()
                            , getMahalCodePostiResultResponse.body().getData()
                            , noeVosolMoshtaryResultResponse.body().getData()
                            , allMoshtaryBrandResultResponse.body().getData()
                    );

                    return true;

                }

        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean getDetailedApis) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updateDetailApiTables(int getProgramType, ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels
            , ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels
            , ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels
            , ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels
            , ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        updateMoshtaryEtebarPishfarzTable(getProgramType, moshtaryEtebarPishFarzModels
                , noeFaaliatForMoarefiMoshtaryJadidModels
                , moshtaryJadidDarkhastModels
                , gorohKalaNoeSenfModels
                , markazShahrMarkaziModels
                , noeMoshtaryRialKharidModels
                , mahalCodePostiModels
                , noeVosolMoshtaryModels
                , moshtaryBrandModels);


    }

    private void updateMoshtaryEtebarPishfarzTable(int getProgramType, ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels
            , ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels
            , ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels
            , ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels
            , ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        MoshtaryEtebarPishFarzRepository moshtaryEtebarPishFarzRepository = new MoshtaryEtebarPishFarzRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryEtebarPishFarzRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryEtebarPishFarzRepository.insertGroup(moshtaryEtebarPishFarzModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "  updateMoshtaryEtebarPishfarzTable:");
                                        updateNoeFaaliatForMoarefiMoshtaryTable(getProgramType
                                                , noeFaaliatForMoarefiMoshtaryJadidModels
                                                , moshtaryJadidDarkhastModels
                                                , gorohKalaNoeSenfModels
                                                , markazShahrMarkaziModels
                                                , noeMoshtaryRialKharidModels
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryEtebarPishfarzTable:error");
                                        throwException("updateMoshtaryEtebarPishfarzTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryEtebarPishfarzTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryEtebarPishfarzTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryEtebarPishfarzTable:error");

                        throwException("updateMoshtaryEtebarPishfarzTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateNoeFaaliatForMoarefiMoshtaryTable(int getProgramType, ArrayList<NoeFaaliatForMoarefiMoshtaryJadidModel> noeFaaliatForMoarefiMoshtaryJadidModels
            , ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels
            , ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels
            , ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        NoeFaaliatForMoarefiMoshtaryJadidRepository noeFaaliatForMoarefiMoshtaryJadidRepository = new NoeFaaliatForMoarefiMoshtaryJadidRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = noeFaaliatForMoarefiMoshtaryJadidRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = noeFaaliatForMoarefiMoshtaryJadidRepository.insertGroup(noeFaaliatForMoarefiMoshtaryJadidModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateNoeFaaliatForMoarefiMoshtaryTable:");
                                        updateMoshtaryJadidDarkhastTable(getProgramType
                                                , moshtaryJadidDarkhastModels
                                                , gorohKalaNoeSenfModels
                                                , markazShahrMarkaziModels
                                                , noeMoshtaryRialKharidModels
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeFaaliatForMoarefiMoshtaryTable:error");
                                        throwException("updateNoeFaaliatForMoarefiMoshtaryTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateNoeFaaliatForMoarefiMoshtaryTable:" + throwable.getMessage());
                                    throwException("updateNoeFaaliatForMoarefiMoshtaryTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeFaaliatForMoarefiMoshtaryTable:error");

                        throwException("updateNoeFaaliatForMoarefiMoshtaryTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMoshtaryJadidDarkhastTable(int getProgramType
            , ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels
            , ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels
            , ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        MoshtaryJadidDarkhastRepository moshtaryJadidDarkhastRepository = new MoshtaryJadidDarkhastRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryJadidDarkhastRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryJadidDarkhastRepository.insertGroup(moshtaryJadidDarkhastModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMoshtaryJadidDarkhastTable:");

                                        updateGorohKalaNoeSenfTable(getProgramType
                                                , gorohKalaNoeSenfModels
                                                , markazShahrMarkaziModels
                                                , noeMoshtaryRialKharidModels
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryJadidDarkhastTable:error");
                                        throwException("updateMoshtaryJadidDarkhastTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryJadidDarkhastTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryJadidDarkhastTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryJadidDarkhastTable:error");

                        throwException("updateMoshtaryJadidDarkhastTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateGorohKalaNoeSenfTable(int getProgramType, ArrayList<GorohKalaNoeSenfModel> gorohKalaNoeSenfModels
            , ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        GorohKalaNoeSenfRepository gorohKalaNoeSenfRepository = new GorohKalaNoeSenfRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = gorohKalaNoeSenfRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = gorohKalaNoeSenfRepository.insertGroup(gorohKalaNoeSenfModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateGorohKalaNoeSenfTable:");
                                        updateMarkazShahrMarkaziTable(getProgramType
                                                , markazShahrMarkaziModels
                                                , noeMoshtaryRialKharidModels
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);
                                    } else {

                                        throwException("updateGorohKalaNoeSenfTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateGorohKalaNoeSenfTable:" + throwable.getMessage());
                                    throwException("updateGorohKalaNoeSenfTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateGorohKalaNoeSenfTable:error");

                        throwException("updateGorohKalaNoeSenfTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMarkazShahrMarkaziTable(int getProgramType, ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels
            , ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        MarkazShahrMarkaziRepository markazShahrMarkaziRepository = new MarkazShahrMarkaziRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = markazShahrMarkaziRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = markazShahrMarkaziRepository.insertGroup(markazShahrMarkaziModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter" + itemCounter + "\t updateMarkazShahrMarkaziTable");
                                        updateNoeMoshtaryRialKharidTable(getProgramType
                                                , noeMoshtaryRialKharidModels
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMarkazShahrMarkaziTable:error");
                                        throwException("updateMarkazShahrMarkaziTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateGorohKalaNoeSenfTable:" + throwable.getMessage());
                                    throwException("updateMarkazShahrMarkaziTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMarkazShahrMarkaziTable:error");

                        throwException("updateMarkazShahrMarkaziTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateNoeMoshtaryRialKharidTable(int getProgramType, ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels
            , ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        NoeMoshtaryRialKharidRepository noeMoshtaryRialKharidRepository = new NoeMoshtaryRialKharidRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = noeMoshtaryRialKharidRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = noeMoshtaryRialKharidRepository.insertGroup(noeMoshtaryRialKharidModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateNoeMoshtaryRialKharidTable");
                                        updateMahalCodePostiTable(getProgramType
                                                , mahalCodePostiModels
                                                , noeVosolMoshtaryModels
                                                , moshtaryBrandModels);

                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeMoshtaryRialKharidTable:error");
                                        throwException("updateNoeMoshtaryRialKharidTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateGorohKalaNoeSenfTable:" + throwable.getMessage());
                                    throwException("updateNoeMoshtaryRialKharidTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeMoshtaryRialKharidTable:error");
                        throwException("updateNoeMoshtaryRialKharidTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateNoeVosolMoshtaryTable(int getProgramType, ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        NoeVosolMoshtaryRepository noeVosolMoshtaryRepository = new NoeVosolMoshtaryRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = noeVosolMoshtaryRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = noeVosolMoshtaryRepository.insertGroup(noeVosolMoshtaryModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateNoeVosolMoshtaryTable:");
                                        updateMoshtaryBrandTable(getProgramType
                                                , moshtaryBrandModels);
//
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeVosolMoshtaryTable:error");
                                        throwException("updateNoeVosolMoshtaryTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateNoeVosolMoshtaryTable:" + throwable.getMessage());
                                    throwException("updateNoeVosolMoshtaryTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateNoeVosolMoshtaryTable:error");

                        throwException("updateNoeVosolMoshtaryTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMahalCodePostiTable(int getProgramType, ArrayList<MahalCodePostiModel> mahalCodePostiModels
            , ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels
            , ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        MahalCodePostiRepository mahalCodePostiRepository = new MahalCodePostiRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = mahalCodePostiRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = mahalCodePostiRepository.insertGroup(mahalCodePostiModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMahalCodePostiTable:");

                                        updateNoeVosolMoshtaryTable(getProgramType, noeVosolMoshtaryModels, moshtaryBrandModels);

//                                                updateMoshtaryBrandTable(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMahalCodePostiTable:error");
                                        throwException("updateMahalCodePostiTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMahalCodePostiTable:" + throwable.getMessage());
                                    throwException("updateMahalCodePostiTable");
                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMahalCodePostiTable:error");

                        throwException("updateMahalCodePostiTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void updateMoshtaryBrandTable(int getProgramType, ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
        MoshtaryBrandRepository moshtaryBrandRepository = new MoshtaryBrandRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryBrandRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryBrandRepository.insertGroup(moshtaryBrandModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMoshtaryBrandTable:");
                                        getAllConfigNoeVosolAndMarjoeeMamorPakhsh(getProgramType);


                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryBrandTable:error");
                                        throwException("updateMoshtaryBrandTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryBrandTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryBrandTable");
                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryBrandTable:error");
                        throwException("updateMoshtaryBrandTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void getAllConfigNoeVosolAndMarjoeeMamorPakhsh(int getProgramType) {

        new MoshtaryRepository(mPresenter.getAppContext())
                .getAllccNoeSenf()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String ccNoeMoshtaries) {
                        fetchConfigNoeVosolApis(getProgramType, ccNoeMoshtaries);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        throwException("getAllConfigNoeVosolAndMarjoeeMamorPakhsh");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void fetchConfigNoeVosolApis(int getProgramType, String ccNoeMoshtaries) {
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getConfigNoeVosolMojazefaktor()
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchConfigNoeVosolApis", "getConfigNoeVosolMojazefaktor"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getConfigNoeVosolMojazefaktorResult -> ++webCounter[0])

                , apiServiceRxjava.getConfigNoeVosolMojazeMoshtary(ccNoeMoshtaries)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchConfigNoeVosolApis", "getConfigNoeVosolMojazeMoshtary"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getConfigNoeVosolMojazeMoshtaryResult -> ++webCounter[0])
                , (getConfigNoeVosolMojazefaktorResultResponse, getConfigNoeVosolMojazeMoshtaryResultResponse) -> {

                    updateConfigNoeVosolTables(getProgramType, getConfigNoeVosolMojazefaktorResultResponse.body(), getConfigNoeVosolMojazeMoshtaryResultResponse.body());
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updateConfigNoeVosolTables(int getProgramType, GetConfigNoeVosolMojazefaktorResult getConfigNoeVosolMojazefaktorResult, GetConfigNoeVosolMojazeMoshtaryResult getConfigNoeVosolMojazeMoshtaryResult) {

        if (getConfigNoeVosolMojazefaktorResult != null) {
            ConfigNoeVosolMojazeFaktorRepository configNoeVosolMojazeFaktorRepository = new ConfigNoeVosolMojazeFaktorRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = configNoeVosolMojazeFaktorRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = configNoeVosolMojazeFaktorRepository.insertGroup(getConfigNoeVosolMojazefaktorResult.getData())
                                    .subscribe(insertGroup ->
                                    {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "updateConfigNoeVosolFaktorTable" + itemCounter);
                                            updateConfigNoeVosolMoshtaryTable(getProgramType, getConfigNoeVosolMojazeMoshtaryResult);

                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolFaktorTable:error");
                                            throwException("updateConfigNoeVosolFaktorTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolFaktorTable:" + throwable.getMessage());
                                        throwException("updateConfigNoeVosolFaktorTable");

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolFaktorTable:error");
                            throwException("updateConfigNoeVosolFaktorTable");
                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "updateConfigNoeVosolTables" + itemCounter);
            updateConfigNoeVosolMoshtaryTable(getProgramType, getConfigNoeVosolMojazeMoshtaryResult);
        }
    }


    private void updateConfigNoeVosolMoshtaryTable(int getProgramType, GetConfigNoeVosolMojazeMoshtaryResult getConfigNoeVosolMojazeMoshtaryResult) {
        if (getConfigNoeVosolMojazeMoshtaryResult != null) {
            ConfigNoeVosolMojazeMoshtaryRepository configNoeVosolMojazeMoshtaryRepository = new ConfigNoeVosolMojazeMoshtaryRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = configNoeVosolMojazeMoshtaryRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = configNoeVosolMojazeMoshtaryRepository.insertGroup(getConfigNoeVosolMojazeMoshtaryResult.getData())
                                    .subscribe(insertGroup ->
                                    {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "updateConfigNoeVosolMoshtaryTable" + itemCounter);
                                            getMarjoeeMamorPakhshRx(getProgramType);

                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolMoshtaryTable:error");
                                            throwException("updateConfigNoeVosolMoshtaryTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolMoshtaryTable:" + throwable.getMessage());
                                        throwException("updateConfigNoeVosolMoshtaryTable");

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateConfigNoeVosolMoshtaryTable:error");
                            throwException("updateConfigNoeVosolMoshtaryTable");
                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "updateConfigNoeVosolMoshtaryTable" + itemCounter);
            getMarjoeeMamorPakhshRx(getProgramType);

        }

    }


    private void getMarjoeeMamorPakhshRx(int getProgramType) {


        Disposable disposable = new DarkhastFaktorRepository(mPresenter.getAppContext()).getCcMoshtaryForZanjire()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ccMoshtaryForZanjire -> {
                    fetchMarjoeeMamorPakhsh(getProgramType, ccMoshtaryForZanjire);
                }, throwable -> throwException(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }


    private void fetchMarjoeeMamorPakhsh(int getProgramType, String ccMoshtaryForZanjire) {
        final int[] webCounter = {itemCounter};
        apiServiceRxjava.getMarjoeeMamorPakhsh(ccMoshtaryForZanjire)
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchMarjoeeMamorPakhsh", "getMarjoeeMamorPakhsh"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<MarjoeeMamorPakhshResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<MarjoeeMamorPakhshResult> marjoeeMamorPakhshResultResponse) {
                        updateMarjoeeMamorPakhshTable(getProgramType, marjoeeMamorPakhshResultResponse.body());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void updateMarjoeeMamorPakhshTable(int getProgramType, MarjoeeMamorPakhshResult marjoeeMamorPakhshResult) {
        if (marjoeeMamorPakhshResult != null) {
            MarjoeeMamorPakhshRepository marjoeeMamorPakhshRepository = new MarjoeeMamorPakhshRepository(mPresenter.getAppContext());
            Disposable deleteAllDisposable = marjoeeMamorPakhshRepository.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(deleteAll -> {
                        if (deleteAll) {
                            Disposable insertGroupDisposable = marjoeeMamorPakhshRepository.insertGroup(marjoeeMamorPakhshResult.getData())
                                    .subscribe(insertGroup ->
                                    {
                                        if (insertGroup) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            Log.i("RxJavaRequest", "updateMarjoeeMamorPakhshTable" + itemCounter);
                                            deleteDarkhastFaktorRoozSortRx(getProgramType);


                                        } else {
                                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeMamorPakhshTable:error");
                                            throwException("updateMarjoeeMamorPakhshTable");
                                        }
                                    }, throwable -> {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeMamorPakhshTable:" + throwable.getMessage());
                                        throwException("updateMarjoeeMamorPakhshTable");

                                    });
                            compositeDisposable.add(insertGroupDisposable);

                        } else {
                            Log.i("RxJavaRequest", +itemCounter + "  updateMarjoeeMamorPakhshTable:error");
                            throwException("updateMarjoeeMamorPakhshTable");
                        }
                    });
            compositeDisposable.add(deleteAllDisposable);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            Log.i("RxJavaRequest", "updateConfigNoeVosolMoshtaryTable" + itemCounter);
            deleteDarkhastFaktorRoozSortRx(getProgramType);

        }

    }


    private void deleteElamMarjoeeTedad(final int getProgramType) {
        final ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = elamMarjoeeSatrPPCTedadDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryEtebarPishfarz(getProgramType, String.valueOf(ccForoshandeh));
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getMoshtaryEtebarPishfarz(final int getProgramType, final String ccForoshandeh) {
        final MoshtaryEtebarPishFarzDAO moshtaryEtebarPishFarzDAO = new MoshtaryEtebarPishFarzDAO(mPresenter.getAppContext());
        moshtaryEtebarPishFarzDAO.fetchMoshtaryEtebarPishFarz(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryEtebarPishFarzDAO.deleteAll();
                        boolean insertResult = moshtaryEtebarPishFarzDAO.insertGroup(arrayListData);
                        Log.d("GetProgram", "MoshtaryEtebarPishfarz: " + arrayListData.toString());
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeFaaliatForMoarefiMoshtaryJadid(getProgramType, String.valueOf(ccForoshandeh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getNoeFaaliatForMoarefiMoshtaryJadid(final int getProgramType, final String ccForoshandeh) {
        final NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        noeFaaliatForMoarefiMoshtaryJadidDAO.fetchNoeFaaliatForMoarefiMoshtaryJadid(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeFaaliatForMoarefiMoshtaryJadidDAO.deleteAll();
                        boolean insertResult = noeFaaliatForMoarefiMoshtaryJadidDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryJadidDarkhast(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryJadidDarkhast(final int getProgramType) {
        final MoshtaryJadidDarkhastDAO moshtaryJadidDarkhastDAO = new MoshtaryJadidDarkhastDAO(mPresenter.getAppContext());
        moshtaryJadidDarkhastDAO.fetchMoshtaryJadidDarkhast(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryJadidDarkhastDAO.deleteAll();
                        boolean insertResult = moshtaryJadidDarkhastDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            deleteDarkhastFaktorRoozSort(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void deleteDarkhastFaktorRoozSortRx(int getProgramType) {
        DarkhastFaktorRoozSortRepository darkhastFaktorRoozSortRepository = new DarkhastFaktorRoozSortRepository(mPresenter.getAppContext());
        Disposable disposable = darkhastFaktorRoozSortRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        getTafkikJozeApiRx(getProgramType);
                    } else {
                        throwException("deleteDarkhastFaktorRoozSortRx");
                    }
                }, throwable -> throwException("deleteDarkhastFaktorRoozSortRx"));

        compositeDisposable.add(disposable);
    }

    private void getTafkikJozeApiRx(int getProgramType) {
        final int[] webCounter = {itemCounter};
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD && !ccDarkhastFaktors.trim().equals("-1")) {

            apiServiceRxjava.getTafkikJozePakhsh(ccDarkhastFaktors)
                    .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getTafkikJozeApiRx", ""))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<GetTafkikJozePakhshResult>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull Response<GetTafkikJozePakhshResult> getTafkikJozePakhshResultResponse) {

                            updateTafkikJozeTable(getProgramType, getTafkikJozePakhshResultResponse.body().getData());

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getMessage(), e.getCause().getMessage()));

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getCrispSupportRx(getProgramType);
        }


    }

    private void updateTafkikJozeTable(int getProgramType, ArrayList<TafkikJozeModel> tafkikJozeModels) {
        Log.i("RxJavaRequest", +itemCounter + "updateTafkikJozeTable:" + itemCounter);

        TafkikJozeRepository tafkikJozeRepository = new TafkikJozeRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = tafkikJozeRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = tafkikJozeRepository.insertGroup(tafkikJozeModels)
                                .subscribe(insertGroup -> {


                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateTafkikJozeTable");
                                        getCrispSupportRx(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateTafkikJozeTable:error");
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateTafkikJozeTable:" + throwable.getMessage());
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateTafkikJozeTable:error");
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    private void getCrispSupportRx(int getProgramType) {


        int ccSazmanForosh = 0;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD)
            ccSazmanForosh = this.ccSazmanForosh;
        apiServiceRxjava.getSupportCrisp(ccSazmanForosh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<SupportCrispResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<SupportCrispResult> supportCrispResultResponse) {

                        updateCrispTable(getProgramType, supportCrispResultResponse.body().getData());


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void updateCrispTable(int getProgramType, ArrayList<SupportCrispModel> supportCrispModels) {
        SupportCrispRepository supportCrispRepository = new SupportCrispRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = supportCrispRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = supportCrispRepository.insertGroup(supportCrispModels)
                                .subscribe(insertGroup -> {
                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t  updateCrispTable");
                                        getAllMoshtaryGharardadAndGharardadKala(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateCrispTable:error");
                                        throwException("updateCrispTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateCrispTable:" + throwable.getMessage());
                                    throwException("updateCrispTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateCrispTable:error");
                        throwException("updateCrispTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    private void deleteDarkhastFaktorRoozSort(int getProgramType) {
        DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(mPresenter.getAppContext());
        darkhastFaktorRoozSortDAO.deleteAll();
        getTafkikJozePakhsh(getProgramType);
    }

    private void getTafkikJozePakhsh(final int getProgramType) {
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD && !ccDarkhastFaktors.trim().equals("-1")) {
            final TafkikJozeDAO tafkikJozeDAO = new TafkikJozeDAO(mPresenter.getAppContext());
            tafkikJozeDAO.fetchTafkikJozePakhsh(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktors, new RetrofitResponse() {
                @Override
                public void onSuccess(ArrayList arrayListData) {
                    boolean deleteResult = tafkikJozeDAO.deleteAll();
                    boolean insertResult = tafkikJozeDAO.insertGroup(arrayListData);
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getGorohKalaNoeSenf(getProgramType);
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getGorohKalaNoeSenf(getProgramType);
        }
    }

    private void getGorohKalaNoeSenf(final int getProgramType) {
        final GorohKalaNoeSenfDAO gorohKalaNoeSenfDAO = new GorohKalaNoeSenfDAO(mPresenter.getAppContext());
        gorohKalaNoeSenfDAO.fetchAllGorohKalaNoeSenf(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = gorohKalaNoeSenfDAO.deleteAll();
                boolean insertResult = gorohKalaNoeSenfDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryBrand(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryBrand(final int getProgramType) {
        String allccMoshtary = ccMoshtarys;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            allccMoshtary = ccMoshtaryPakhsh;
        }
        final MoshtaryBrandDAO moshtaryBrandDAO = new MoshtaryBrandDAO(mPresenter.getAppContext());
        moshtaryBrandDAO.fetchMoshtaryBrand(mPresenter.getAppContext(), activityNameForLog, allccMoshtary, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = moshtaryBrandDAO.deleteAll();
                boolean insertResult = moshtaryBrandDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getAllMarkazShahrMarkazi(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllMarkazShahrMarkazi(final int getProgramType) {
        final MarkazShahrMarkaziDAO markazShahrMarkaziDAO = new MarkazShahrMarkaziDAO(mPresenter.getAppContext());
        markazShahrMarkaziDAO.fetchMarkazShahrMarkazi(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = markazShahrMarkaziDAO.deleteAll();
                boolean insertResult = markazShahrMarkaziDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    Log.d("GetProgram", "getProgramType:" + getProgramType + " , ccMarkazSazmanForoshSakhtarForosh hadeaghal " + ccMarkazSazmanForoshSakhtarForosh);
                    getAllNoeMoshtaryRialKharid(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllNoeMoshtaryRialKharid(final int getProgramType, String ccMarkazSazmanForoshSakhtarForoshs) {
        final NoeMoshtaryRialKharidDAO noeMoshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(mPresenter.getAppContext());
        /*sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
        getParameter(getProgramType);*/
        noeMoshtaryRialKharidDAO.fetchNoeMoshtaryRialKharid(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForoshs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = noeMoshtaryRialKharidDAO.deleteAll();
                boolean insertResult = noeMoshtaryRialKharidDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getAllcodePosti(getProgramType, ccMarkazForosh);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllcodePosti(final int getProgramType, int ccMarkazForosh) {
        final MahalCodePostiDAO mahalCodePostiDAO = new MahalCodePostiDAO(mPresenter.getAppContext());
        mahalCodePostiDAO.fetchMahalCodePosti(mPresenter.getAppContext(), activityNameForLog, ccMarkazForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mahalCodePostiDAO.deleteAll();
                        boolean insertResult = mahalCodePostiDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD)
                                getSupportCrisp(getProgramType, 0);
                            else
                                getSupportCrisp(getProgramType, ccSazmanForosh);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getSupportCrisp(final int getProgramType, int ccSazmanForosh) {
        final SupportCrispDAO supportCrispDAO = new SupportCrispDAO(mPresenter.getAppContext());
        supportCrispDAO.fetchSupportCrisp(mPresenter.getAppContext(), activityNameForLog, ccSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = supportCrispDAO.deleteAll();
                        boolean insertResult = supportCrispDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            Log.d("GetProgram", "ccMarkazSazmanForosh" + ccMarkazSazmanForosh);
                            Log.d("GetProgram", "ccMarkazSazmanForosh" + ccMarkazSazmanForoshPakhsh);
                            getNoeVosolMoshtary(getProgramType, ccMarkazSazmanForosh);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeVosolMoshtary(final int getProgramType, int ccMarkazSazmanForosh) {
        final NoeVosolMoshtaryDAO noeVosolMoshtaryDAO = new NoeVosolMoshtaryDAO(mPresenter.getAppContext());
        noeVosolMoshtaryDAO.fetchNoeVosolMoshtary(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeVosolMoshtaryDAO.deleteAll();
                        boolean insertResult = noeVosolMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            getParameter(getProgramType);
//                            getAllMoshtaryGharardad(getProgramType, ccForoshandeh);

                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllMoshtaryGharardadAndGharardadKala(int getProgramType) {
        apiServiceRxjava.getMoshtaryGharardad(String.valueOf(ccForoshandeh))
                .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "getAllMoshtaryGharardadAndGharardadKala", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetAllMoshtaryGharardadResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllMoshtaryGharardadResult> getAllMoshtaryGharardadResultResponse) {
                        updateMoshtaryGhararadTable(getProgramType, getAllMoshtaryGharardadResultResponse.body().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", e.getMessage(), e.getCause().getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void updateMoshtaryGhararadTable(int getProgramType, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        MoshtaryGharardadRepository moshtaryGharardadRepository = new MoshtaryGharardadRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryGharardadRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryGharardadRepository.insertGroup(moshtaryGharardadModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", "itemCounter:" + itemCounter + "\t updateMoshtaryGhararadTable:");
                                        getAllMoshtaryGharardadKala(getProgramType, moshtaryGharardadModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGhararadTable:error");
                                        throwException("updateMoshtaryGhararadTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGhararadTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryGhararadTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGhararadTable:error");
                        throwException("updateMoshtaryGhararadTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    private void getAllMoshtaryGharardadKala(int getProgramType, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};
        Observable.fromIterable(moshtaryGharardadModels)
                .flatMap(moshtaryGharardadModel ->  apiServiceRxjava.getMoshtaryGharardadKala(String.valueOf(moshtaryGharardadModel.getCcSazmanForosh()), String.valueOf(moshtaryGharardadModel.getCcMoshtaryGharardad()))
                        .doOnNext(getAllMoshtaryGharardadKalaResultResponse -> getAllMoshtaryGharardadKalaResultResponse.body().setExtraPropccSazmanForosh(moshtaryGharardadModel.getCcSazmanForosh()))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetAllMoshtaryGharardadKalaResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<GetAllMoshtaryGharardadKalaResult> getAllMoshtaryGharardadKalaResultResponse) {
                        if (getAllMoshtaryGharardadKalaResultResponse.isSuccessful()) {
                            moshtaryGharardadKalaModels.addAll(getAllMoshtaryGharardadKalaResultResponse.body().getData());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(++webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getMessage(), e.getCause().getMessage()));

                    }

                    @Override
                    public void onComplete() {
                        updateMoshtaryGharardadKalaTable(getProgramType, moshtaryGharardadKalaModels);
                    }
                });

    }

    private void updateMoshtaryGharardadKalaTable(int getProgramType, ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels) {
        MoshtaryGharardadKalaRepository moshtaryGharardadKalaRepository = new MoshtaryGharardadKalaRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = moshtaryGharardadKalaRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = moshtaryGharardadKalaRepository.insertGroup(moshtaryGharardadKalaModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateMoshtaryGharardadKalaTable:" + insertGroup);

//
                                        getParametersAndBarkhordApis(getProgramType);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGharardadKalaTable:error");
                                        throwException("updateMoshtaryGharardadKalaTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGharardadKalaTable:" + throwable.getMessage());
                                    throwException("updateMoshtaryGharardadKalaTable");
                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateMoshtaryGharardadKalaTable:error");
                        throwException("updateMoshtaryGharardadKalaTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }

    private void getParametersAndBarkhordApis(int getProgramType) {


        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        final String lastDateTimeGetConfig = getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), "20190101 00:00:00");


        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            ccMarkazAnbar = -1;
        } else {
            ccMarkazSazmanForosh = -1;
        }
        Disposable disposable = new ParameterChildRepository(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL)
                .subscribe(tedadMah -> {
                    tedadMah = (tedadMah == null || tedadMah.trim().equals("")) ? "1" : tedadMah;
                    fetchParametersAndBarkhord(getProgramType, tedadMah, lastDateTimeGetConfig);
                }, throwable -> sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter));
        compositeDisposable.add(disposable);


    }

    private void fetchParametersAndBarkhord(int getProgramType, String tedadMah, String lastDateTimeGetConfig) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<>();
        ArrayList<ParameterChildModel> parameterChildModels = new ArrayList<>();
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels = new ArrayList<>();
        final int[] webCounter = {itemCounter};
        Observable.zip(apiServiceRxjava.getParameter("1", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchParametersAndBarkhord", "getParameter"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getParameterResultResponse -> {
                            webCounter[0]++;
                        })

                ,
                apiServiceRxjava.getParameterChild("3", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchParametersAndBarkhord", "getParameterChild"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getParameterResultResponse -> {
                            webCounter[0]++;
                        })

                ,
                apiServiceRxjava.getBarkhordForoshandehBaMoshtary(String.valueOf(ccForoshandeh), ccMoshtarys, tedadMah)
                        .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, ACTIVITY_NAME, "fetchParametersAndBarkhord", "getBarkhordForoshandehBaMoshtary"))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(getParameterResultResponse -> {
                            webCounter[0]++;
                        })
                        .subscribeOn(Schedulers.io()), new Function3<Response<GetParameterResult>, Response<GetParameterChildResult>, Response<BarkhordForoshandehBaMoshtaryResult>, Boolean>() {
                    @NonNull
                    @Override
                    public Boolean apply(@NonNull Response<GetParameterResult> getParameterResultResponse, @NonNull Response<GetParameterChildResult> getParameterChildResultResponse, @NonNull Response<BarkhordForoshandehBaMoshtaryResult> barkhordForoshandehBaMoshtaryResultResponse) {


                        parameterModels.addAll(getParameterResultResponse.body().getData());
                        parameterChildModels.addAll(getParameterChildResultResponse.body().getData());
                        barkhordForoshandehBaMoshtaryModels.addAll(barkhordForoshandehBaMoshtaryResultResponse.body().getData());
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        updateParameterTable(getProgramType, parameterModels, parameterChildModels, barkhordForoshandehBaMoshtaryModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedGetProgram(webCounter[0], String.format(" type : %1$s \n error : %2$s", e.getCause().getMessage(), e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updateParameterTable(int getProgramType, ArrayList<ParameterModel> parameterModels
            , ArrayList<ParameterChildModel> parameterChildModels
            , ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {
        ParameterRepository parameterRepository = new ParameterRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = parameterRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = parameterRepository.insertGroup(parameterModels)
                                .subscribe(insertGroup -> {
                                    Log.i("RxJavaRequest", +itemCounter + "parameterRepository:" + insertGroup);

                                    if (insertGroup) {
                                        updateParameterChildTable(getProgramType, parameterChildModels, barkhordForoshandehBaMoshtaryModels);
                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  parameterRepository:error");
                                        throwException("updateParameterTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  parameterRepository:" + throwable.getMessage());
                                    throwException("updateParameterTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  parameterRepository:error");
                        throwException("updateParameterTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);


    }

    private void updateParameterChildTable(int getProgramType, ArrayList<ParameterChildModel> parameterChildModels, ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {
        ParameterChildRepository parameterChildRepository = new ParameterChildRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = parameterChildRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = parameterChildRepository.insertGroup(parameterChildModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateParameterChildTable:");
                                        updateBarkhordForoshandehBaMoshtaryTable(getProgramType, barkhordForoshandehBaMoshtaryModels);


                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateParameterChildTable:error");
                                        throwException("updateParameterChildTable");
                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateParameterChildTable:" + throwable.getMessage());
                                    throwException("updateParameterChildTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateParameterChildTable:error");

                        throwException("updateParameterChildTable");
                    }
                });
        compositeDisposable.add(deleteAllDisposable);

    }

    private void updateBarkhordForoshandehBaMoshtaryTable(int getProgramType, ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels) {

        BarkhordForoshandehBaMoshtaryRepository barkhordForoshandehBaMoshtaryRepository = new BarkhordForoshandehBaMoshtaryRepository(mPresenter.getAppContext());
        Disposable deleteAllDisposable = barkhordForoshandehBaMoshtaryRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(deleteAll -> {
                    if (deleteAll) {
                        Disposable insertGroupDisposable = barkhordForoshandehBaMoshtaryRepository.insertGroup(barkhordForoshandehBaMoshtaryModels)
                                .subscribe(insertGroup -> {

                                    if (insertGroup) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.i("RxJavaRequest", +itemCounter + "updateBarkhordForoshandehBaMoshtaryTable:" + insertGroup);

                                        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                            saveLastGetProgramDate();
                                            updateJayezehTakhfifVersion();
                                            setLogToDB(Constants.LOG_EXCEPTION(), "GetProgram Success", "GetProgramModel", "", "getBarkhordForoshandehBaMoshtary", "onSuccess");
                                        }
                                        checkLastOlaviat();


                                    } else {
                                        Log.i("RxJavaRequest", +itemCounter + "  updateBarkhordForoshandehBaMoshtaryTable:error");
                                        throwException("updateBarkhordForoshandehBaMoshtaryTable");

                                    }
                                }, throwable -> {
                                    Log.i("RxJavaRequest", +itemCounter + "  updateBarkhordForoshandehBaMoshtaryTable:" + throwable.getMessage());
                                    throwException("updateBarkhordForoshandehBaMoshtaryTable");

                                });
                        compositeDisposable.add(insertGroupDisposable);

                    } else {
                        Log.i("RxJavaRequest", +itemCounter + "  updateBarkhordForoshandehBaMoshtaryTable:error");

                        throwException("updateBarkhordForoshandehBaMoshtaryTable");

                    }
                });
        compositeDisposable.add(deleteAllDisposable);
    }


    private void getParameter(final int getProgramType) {
        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        final String lastDateTimeGetConfig = getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), "20190101 00:00:00");

        final ParameterDAO parameterDAO = new ParameterDAO(mPresenter.getAppContext());
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3) {
            ccMarkazAnbar = -1;
        } else {
            ccMarkazSazmanForosh = -1;
        }
        parameterDAO.fetchParameter(mPresenter.getAppContext(), "GetProgramActivity", "1", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (arrayListData.size() > 1) {
                            boolean deleteResult = parameterDAO.deleteAll();
                            boolean insertResult = parameterDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                getParameterChild(getProgramType, lastDateTimeGetConfig, String.valueOf(ccMarkazSazmanForosh));
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        } else {
                            getParameterChild(getProgramType, lastDateTimeGetConfig, String.valueOf(ccMarkazSazmanForosh));
                            //sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getParameterChild(final int getProgramType, String lastDateTimeGetConfig, String ccMarkazSazmanForosh) {
        final ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            ccMarkazAnbar = -1;
        } else {
            ccMarkazSazmanForosh = "-1";
        }
        parameterChildDAO.fetchParameterChild(mPresenter.getAppContext(), "GetProgramActivity", "3", ccMarkazSazmanForosh, String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                if (arrayListData.size() > 0) {
                    boolean deleteResult = parameterChildDAO.deleteAll();
                    boolean insertResult = parameterChildDAO.insertGroup(arrayListData);
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

                        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                            getBarkhordForoshandehBaMoshtary(getProgramType, String.valueOf(ccForoshandeh));
                        }
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                        getBarkhordForoshandehBaMoshtary(getProgramType, String.valueOf(ccForoshandeh));
                    }
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getBarkhordForoshandehBaMoshtary(final int getProgramType, String ccForoshandeh) {
        String tedadMah = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL);
        tedadMah = (tedadMah == null || tedadMah.trim().equals("")) ? "1" : tedadMah;
        final BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        barkhordForoshandehBaMoshtaryDAO.fetchBarkhordForoshandehBaMoshtary(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMoshtarys, tedadMah, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {

                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        for (int i = 0; i < arrayListData.size(); i++) {
                            ((BarkhordForoshandehBaMoshtaryModel) arrayListData.get(i)).setExtraProp_IsOld(1);
                        }
                        boolean deleteResult = barkhordForoshandehBaMoshtaryDAO.deleteAll();
                        boolean insertResult = barkhordForoshandehBaMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                saveLastGetProgramDate();
                                updateJayezehTakhfifVersion();
                                setLogToDB(Constants.LOG_EXCEPTION(), "GetProgram Success", "GetProgramModel", "", "getBarkhordForoshandehBaMoshtary", "onSuccess");
//                                getMoshtaryParent(getProgramType,ccMoshtarys);
                            }
                            checkLastOlaviat();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void checkLastOlaviat() {
        LastOlaviatMoshtaryShared lastOlaviatMoshtaryShared = new LastOlaviatMoshtaryShared(mPresenter.getAppContext());
        Date currentDate = new Date();
        Date lastOlaviatDate = new Date();
        String strLastOlaviatDate = lastOlaviatMoshtaryShared.getString(LastOlaviatMoshtaryShared.TARIKH, "");
        if (strLastOlaviatDate != null && !strLastOlaviatDate.trim().equals("")) {
            try {
                lastOlaviatDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(strLastOlaviatDate);
                Log.d("GetProgram", "lastOlaviatDate : " + lastOlaviatDate);

            } catch (Exception e) {
                Log.i("checkLastOlaviat", "checkLastOlaviat:" + e.getMessage());
                e.printStackTrace();

            }
        }
        long daysOfDiff = DateUtils.getDateDiffAsDay(currentDate, lastOlaviatDate);
        if (daysOfDiff != 0) {
            lastOlaviatMoshtaryShared.removeAll();
        }
    }

    private void saveLastGetProgramDate() {
        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE()).format(new Date());
        getProgramShared.putString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), currentDate);
        Log.d("GetProgram", "currentDate : " + currentDate);
    }


    ////////////////////////// GET PROGRAM AMARGAR //////////////////////////

    private void deletePorseshnameh() {
        new PorseshnamehTablighatDAO(mPresenter.getAppContext()).deleteAll();
        new PorseshnamehDAO(mPresenter.getAppContext()).deleteAll();
        new PorseshnamehShomareshDAO(mPresenter.getAppContext()).deleteAll();
        new VisitMoshtaryDAO(mPresenter.getAppContext()).deleteAll();
        getBrandAmargar();
    }

    private void getBrandAmargar() {
        final BrandDAO brandDAO = new BrandDAO(mPresenter.getAppContext());
        brandDAO.fetchAmargarBrand(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = brandDAO.deleteAll();
                        boolean insertResult = brandDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                            getAmargarGoroh();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAmargarGoroh() {
        final AmargarGorohDAO amargarGorohDAO = new AmargarGorohDAO(mPresenter.getAppContext());
        amargarGorohDAO.fetchamrgarGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = amargarGorohDAO.deleteAll();
                        boolean insertResult = amargarGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaAmargar() {
        final KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        kalaDAO.fetchKalaAmargar(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaDAO.deleteAll();
                        boolean insertResult = kalaDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaGorohAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaGorohAmargar() {
        final KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
        kalaGorohDAO.fetchAllvKalaGorohAmargar(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaGorohDAO.deleteAll();
                        boolean insertResult = kalaGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeTablighat();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeTablighat() {
        final NoeTablighatDAO noeTablighatDAO = new NoeTablighatDAO(mPresenter.getAppContext());
        noeTablighatDAO.fetchNoeTablighat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeTablighatDAO.deleteAll();
                        boolean insertResult = noeTablighatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAmargarMarkazForosh();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAmargarMarkazForosh() {
        final AmargarMarkazSazmanForoshDAO amargarMarkazForoshDAO = new AmargarMarkazSazmanForoshDAO(mPresenter.getAppContext());
        amargarMarkazForoshDAO.fetchAmargarMarkazForosh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(foroshandehMamorPakhshModel.getCcAmargar()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = amargarMarkazForoshDAO.deleteAll();
                        boolean insertResult = amargarMarkazForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMahalAmargar(amargarMarkazForoshDAO);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMahalAmargar(AmargarMarkazSazmanForoshDAO amargarMarkazForoshDAO) {
        final MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        String markazForoshAmargar = amargarMarkazForoshDAO.getAllccMarkazForosh();
        Log.d("GetProgram", "markazForoshAmargar : " + markazForoshAmargar);
        if (markazForoshAmargar.trim().equals("")) {
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
        } else {
            mahalDAO.fetchAllMahalByccMarkazForoshAmargar(mPresenter.getAppContext(), activityNameForLog, markazForoshAmargar, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = mahalDAO.deleteAll();
                            boolean insertResult = mahalDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getRotbehAmargar();
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }

    private void getRotbehAmargar() {
        final RotbehDAO rotbehDAO = new RotbehDAO(mPresenter.getAppContext());
        rotbehDAO.fetchRotbeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rotbehDAO.deleteAll();
                        boolean insertResult = rotbehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getPorseshnameTozihatAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getPorseshnameTozihatAmargar() {
        final PorseshnamehTozihatDAO porseshnamehTozihatDAO = new PorseshnamehTozihatDAO(mPresenter.getAppContext());
        porseshnamehTozihatDAO.fetchPorseshnamehTozihat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = porseshnamehTozihatDAO.deleteAll();
                        boolean insertResult = porseshnamehTozihatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMarkazForoshAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMarkazForoshAmargar() {
        final MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
        markazDAO.fetchAllMarkazForosh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = markazDAO.deleteAll();
                        boolean insertResult = markazDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAllForoshandehAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllForoshandehAmargar() {
        final ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
        foroshandehDAO.fetchAllForoshandeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = foroshandehDAO.deleteAll();
                        boolean insertResult = foroshandehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMasirFaalForoshandehAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMasirFaalForoshandehAmargar() {
        final MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        masirDAO.fetchAllMasirFaalForoshandeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = masirDAO.deleteAll();
                        boolean insertResult = masirDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeFaaliatSenfAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeFaaliatSenfAmargar() {
        final NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        noeFaaliatForMoarefiMoshtaryJadidDAO.fetchNoeFaaliatForMoarefiMoshtaryJadid(mPresenter.getAppContext(), activityNameForLog, "0", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeFaaliatForMoarefiMoshtaryJadidDAO.deleteAll();
                        boolean insertResult = noeFaaliatForMoarefiMoshtaryJadidDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getGorohAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getGorohAmargar() {
        final GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        gorohDAO.fetchAllGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = gorohDAO.deleteAll();
                        boolean insertResult = gorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getElatAdamMoarefiMoshtaryAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getElatAdamMoarefiMoshtaryAmargar() {
        final ElatAdamMoarefiMoshtaryDAO elatAdamMoarefiMoshtaryDAO = new ElatAdamMoarefiMoshtaryDAO(mPresenter.getAppContext());
        elatAdamMoarefiMoshtaryDAO.fetchElatAdamMoarefiMoshtary(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = elatAdamMoarefiMoshtaryDAO.deleteAll();
                        boolean insertResult = elatAdamMoarefiMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }




    private void getModatVosol(final int getProgramType , String ccMarkazSazmanForoshSakhtarForosh , final String ccGorohs)
    {
        Log.d("GetProgram" , "ccGorohs before modat vosol : " + ccGorohs);
        final ModatVosolDAO modatVosolDAO = new ModatVosolDAO(mPresenter.getAppContext());
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            int ccMarkazSazmanForoshSakhtarForoshMamorPakhsh = darkhastFaktorDAO.getccMarkazSazmanForoshSakhtarForosh();
            modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForoshSakhtarForoshMamorPakhsh), ccGorohs, new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            boolean deleteResult = modatVosolDAO.deleteAll();
                            boolean insertResult = modatVosolDAO.insertGroup(arrayListData);

                            int counter = itemCounter;
                            if (getProgramType == Constants.GET_PROGRAM_FULL())
                            {
                                counter = ++itemCounter;
                            }
                            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA())
                            {
                                counter = itemCounter;
                            }
                            if (deleteResult && insertResult)
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , counter);
                                getModatVosolGoroh(getProgramType , ccGorohs);
                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED() , counter);
                            }
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                }
            });
        }
        else
        {
            modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForosh, ccGorohs, new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            boolean deleteResult = modatVosolDAO.deleteAll();
                            boolean insertResult = modatVosolDAO.insertGroup(arrayListData);

                            int counter = itemCounter;
                            if (getProgramType == Constants.GET_PROGRAM_FULL())
                            {
                                counter = ++itemCounter;
                            }
                            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA())
                            {
                                counter = itemCounter;
                            }
                            if (deleteResult && insertResult)
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , counter);
                                getModatVosolGoroh(getProgramType , ccGorohs);
                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED() , counter);
                            }
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                }
            });
        }
    }

    private void getModatVosolGoroh(final int getProgramType , String ccGorohs)
    {
        final ModatVosolGorohDAO modatVosolGorohDAO = new ModatVosolGorohDAO(mPresenter.getAppContext());
        modatVosolGorohDAO.fetchAllModatVosolGoroh(mPresenter.getAppContext(), activityNameForLog, ccGorohs, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = modatVosolGorohDAO.deleteAll();
                        boolean insertResult = modatVosolGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getModatVosolMarkaz(getProgramType , String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getModatVosolMarkaz(final int getProgramType , String ccMarkazSazmanForoshSakhtarForosh)
    {
        final ModatVosolMarkazDAO modatVosolMarkazDAO = new ModatVosolMarkazDAO(mPresenter.getAppContext());
        modatVosolMarkazDAO.fetchAllModatVosolMarkaz(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForosh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = modatVosolMarkazDAO.deleteAll();
                        boolean insertResult = modatVosolMarkazDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getAnbarakInfo(getProgramType , String.valueOf(ccAfrad),String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getAnbarakInfo(final int getProgramType , final String ccAfrad, final String ccMarkazSazmanForoshSakhtarForosh)
    {
        final AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        anbarakAfradDAO.fetchAnbarakAfrad(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = anbarakAfradDAO.deleteAll();
                        boolean insertResult = anbarakAfradDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            if (arrayListData.size() > 0)
                            {
                                anbarakAfrad = String.valueOf(((AnbarakAfradModel)arrayListData.get(0)).getCcAnbarak());
                            }
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);

                                getKala(getProgramType , ccAfrad, ccMarkazSazmanForoshSakhtarForosh);

                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }
    private void getKala(final int getProgramType , String ccAfrad, String ccMarkazSazmanForoshSakhtarForosh)
    {
        final KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        kalaDAO.fetchMojodyAnbar(mPresenter.getAppContext(), activityNameForLog, ccAfrad , ccMarkazSazmanForoshSakhtarForosh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = kalaDAO.deleteAll();
                        boolean insertResult = kalaDAO.insertGroup(arrayListData);
                        for(KalaModel kala : (ArrayList<KalaModel>) arrayListData)
                            Log.d("getProgram" , "KalaModel = " + kala.toString());
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            if (anbarakAfrad.trim().equals("-1"))
                            {
                                //if go to this way, then getkalaolaviat and getAnbarakMojodi didn't call but we show to user that this two items selected in list
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                getKalaMojodi(getProgramType);
                            }
                            else
                            {
                                getKalaolaviat(getProgramType , anbarakAfrad);
                            }
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }





        /*jayezehSatrDAO.fetchJayezehSatr(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = jayezehSatrDAO.deleteAll();
                        boolean insertResult = jayezehSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getJayezehSatr(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/


    private void getKalaMojodi(final int getProgramType)
    {
        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        final KalaMojodiDAO kalamojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = mandehMojodyMashinDAO.getAll();
                kalamojodiDAO.deleteAll();
                ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
                if (mandehMojodyMashinModels.size() > 0)
                {
                    String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
                    for(MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels)
                    {
                        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                        kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
                        kalaMojodiModel.setCcForoshandeh(ccForoshandeh);
                        kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                        kalaMojodiModel.setCcDarkhastFaktor(0);
                        kalaMojodiModel.setTarikhDarkhast(currentDate);
                        kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
                        kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
                        kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
                        kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
                        kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
                        kalaMojodiModel.setZamaneSabt(currentDate);
                        kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
                        kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
                        kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
                        kalaMojodiModel.setCcAfrad(ccAfrad);
                        Log.d("GetProgramModel","ccAfrad:"+ccAfrad);

                        kalaMojodiModels.add(kalaMojodiModel);
                    }
                    boolean insertResult = kalamojodiDAO.insertGroup(kalaMojodiModels);
                    if (insertResult)
                    {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                        getKalaGoroh(getProgramType);
                    }
                    else
                    {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                    }
                }
                else
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getKalaGoroh(getProgramType);
                }
            }
        };
        thread.start();
    }






    private void getKalaGoroh(final int getProgramType)
    {
        final KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
        kalaGorohDAO.fetchAllvKalaGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = kalaGorohDAO.deleteAll();
                        boolean insertResult = kalaGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getZaribForoshKala(getProgramType , ccGorohss);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }




    private void getKalaolaviat(final int getProgramType , final String anbarakAfrad)
    {
        final KalaOlaviatDAO kalaOlaviatDAO = new KalaOlaviatDAO(mPresenter.getAppContext());
        kalaOlaviatDAO.fetchKalaOlaviat(mPresenter.getAppContext(), activityNameForLog, anbarakAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = kalaOlaviatDAO.deleteAll();
                        boolean insertResult = kalaOlaviatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getAnbarakMojodi(getProgramType , anbarakAfrad , String.valueOf(ccForoshandeh) , String.valueOf(ccMamorPakhsh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getZaribForoshKala(final int getProgramType , String ccGorohs)
    {
        final KalaZaribForoshDAO kalaZaribForoshDAO = new KalaZaribForoshDAO(mPresenter.getAppContext());
        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        String ccMarkazForoshKalaZaribForosh = "-1";
        if(noeMasouliat==1 || noeMasouliat==2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat ==8)
            ccMarkazForoshKalaZaribForosh = String.valueOf(foroshandehMamorPakhshDAO.getIsSelect().getCcMarkazForosh());
        else if (noeMasouliat==4 || noeMasouliat==5)
            ccMarkazForoshKalaZaribForosh = ccMarkazForoshPakhsh;
        kalaZaribForoshDAO.fetchAllKalaZaribForosh(mPresenter.getAppContext(), activityNameForLog, ccGorohs, ccMarkazForoshKalaZaribForosh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = kalaZaribForoshDAO.deleteAll();
                        boolean insertResult = kalaZaribForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getJayezeh(getProgramType);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getAnbarakMojodi(final int getProgramType , String anbarakAfrad , String ccForoshandeh , String ccMamorPakhsh)
    {
        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        ccForoshandeh = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        String ccSazmanForosh = String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh());
        String ccKalaCode ="-1";
        if (noeMasouliat == 1 || noeMasouliat == 6 || noeMasouliat ==8)//1-Foroshandeh-Sard
        {
            anbarakAfrad = "0";
            ccMamorPakhsh = "0";
        }
        else if(noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm //3-Foroshandeh-Smart
        {
            ccMamorPakhsh = "0";
        }
        else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
        {
            ccForoshandeh = "0";
        }
        else //6-SarparastForoshandeh 7-Amargar
        {
            ccForoshandeh = "0";
            ccMamorPakhsh = "0";
        }

        Log.d("GetProgram","Online: " + anbarakAfrad + " - " + ccForoshandeh + " - " + ccMamorPakhsh + " - " + ccKalaCode + " - " + ccSazmanForosh);
        mandehMojodyMashinDAO.fetchMandehMojodyMashin(mPresenter.getAppContext(), activityNameForLog, anbarakAfrad, ccForoshandeh, ccMamorPakhsh,ccKalaCode,ccSazmanForosh, new RxResponseHandler() {

            @Override
            public void onStart(Disposable disposable) {
                super.onStart(disposable);
            }

            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mandehMojodyMashinDAO.deleteAll();
                        boolean insertResult = mandehMojodyMashinDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaMojodi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }

    private void getMasir(final int getProgramType , final String ccForoshandeh, String ccMarkazForosh, String azTarikh, String taTarikh, String codeNoe)
    {
        final MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        masirDAO.fetchAllMasir(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMarkazForosh,
                azTarikh, taTarikh, codeNoe, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = masirDAO.deleteAll();
                                boolean insertResult = masirDAO.insertGroup(arrayListData);
                                boolean updateTarikhMasirResult = masirDAO.updateTarikhMasir(date);
                                ArrayList<MasirModel> masirModels = arrayListData;
                                for (MasirModel masir: masirModels)
                                {
                                    ccMasirs += "," + masir.getCcMasir();
                                }

                                if (deleteResult && insertResult && updateTarikhMasirResult)
                                {

                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , itemCounter);
                                        getMoshtary(getProgramType , ccForoshandeh , ccMasirs , "-1");

                                }
                                else
                                {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                    }
                });
    }
    private void getMoshtary(final int getProgramType , String ccForoshandeh , String ccMasir , String codeMoshtary)
    {
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), activityNameForLog,ccForoshandeh, ccMasir, codeMoshtary, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryDAO.deleteAll();
                        boolean insertResult = moshtaryDAO.insertGroup(arrayListData);
                        boolean updateOlaviatResult = moshtaryDAO.updateExtraOlaviatFromOlaviat();
                        boolean updateToorVisitResult = true;//moshtaryDAO.updateToorVisitMoshtary();
                        if (deleteResult && insertResult && updateOlaviatResult && updateToorVisitResult)
                        {
                            for (int i = 0 ; i < arrayListData.size() ; i++)
                            {
                                ccMoshtarys += ((MoshtaryModel)arrayListData.get(i)).getCcMoshtary() + ",";
                            }
                            if (ccMoshtarys.length() != 0)
                            {
                                ccMoshtarys = ccMoshtarys.substring(0, ccMoshtarys.length()-1);
                            }
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryParent(getProgramType , ccMoshtarys);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();

            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }
    private void getMoshtaryParent(final int getProgramType , String ccMoshtarys)
    {


//        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
//        moshtaryDAO.fetchMoshtaryParent(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse()
//        {
//            @Override
//            public void onSuccess(final ArrayList arrayListData)
//            {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                deleteMoshtaryAmargarImage(getProgramType);

//                        int successfulUpdateCounter = 0;
//                        for (int i = 0 ; i < arrayListData.size() ; i++)
//                        {
//                            boolean updateResult = moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData);
//                            if (updateResult)
//                            {
//                                successfulUpdateCounter++;
//                            }
//                        }

//                        boolean result = true;//moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData);
//                        if (result)
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
//                            deleteMoshtaryAmargarImage(getProgramType);
//                        }
//                        else
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
//                        }

            }
        };
        thread.start();
//            }
//            @Override
//            public void onFailed(String type, String error)
//            {
//                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
//            }
//        });
    }


    private void deleteMoshtaryAmargarImage(final int getProgramType)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                MoshtaryAmargarImageDAO moshtaryAmargarImageDAO = new MoshtaryAmargarImageDAO(mPresenter.getAppContext());
                MoshtaryAmargarImageTmpDAO moshtaryAmargarImageTmpDAO = new MoshtaryAmargarImageTmpDAO(mPresenter.getAppContext());

                boolean deleteMoshtaryAmargarImage = moshtaryAmargarImageDAO.deleteAll();
                boolean deleteMoshtaryAmargarImageTemp = moshtaryAmargarImageTmpDAO.deleteAll();

                if (deleteMoshtaryAmargarImage && deleteMoshtaryAmargarImageTemp)
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getMoshtaryAddress(getProgramType , ccMoshtarys , String.valueOf(ccMarkazSazmanForosh));
                }
                else
                {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void getMoshtaryAddress(final int getProgramType , final String ccMoshtarys , String ccMarkazSazmanForosh)
    {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), ccMasirs, "-1", new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryAddressDAO.deleteAll();
                        boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryAfrad(getProgramType , ccMoshtarys);
//                            if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
//                            {
//                                getMoshtaryEtebarSazmanForosh(getProgramType , ccMoshtarys , String.valueOf(ccSazmanForosh));
//                            }
//                            else
//                            {
//                                getMoshtaryAfrad(getProgramType , ccMoshtarys);
//                            }
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getMoshtaryAfrad(final int getProgramType , final String ccMoshtarys)
    {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryAfradDAO.deleteAll();
                        boolean insertResult = moshtaryAfradDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryEtebarSazmanForosh(getProgramType , ccMoshtarys , String.valueOf(ccSazmanForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getMoshtaryEtebarSazmanForosh(final int getProgramType , final String ccMoshtarys , String ccSazmanForosh)
    {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, ccSazmanForosh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteAll();
                        boolean insertResult = moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryGoroh(getProgramType , ccMoshtarys);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }




    private void getMoshtaryGoroh(final int getProgramType , final String ccMoshtarys)
    {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
        if(getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
        {
            getBargashty(getProgramType);
        }

    }


    int globalCounter = 0;
    private void getBargashty(final int getProgramType)
    {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat==6 || noeMasouliat==8)
        {
            bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run(){
                            boolean deleteResult = bargashtyDAO.deleteAll();
                            boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
                                {
                                    getMoshtaryChidman(getProgramType , ccMasirs);
                                }

                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                }
            });
        }
        else if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            boolean deleteResult = bargashtyDAO.deleteAll();
            globalCounter = 0;
            for (String strccForoshandeh : foroshandehArray)
            {
                bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run(){
                                boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                                if (insertResult)
                                {
                                    globalCounter++;
                                    if (globalCounter == foroshandehArray.length)
                                    {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
                                        {
                                            getMoshtaryChidman(getProgramType , ccMasirs);
                                        }

                                    }
                                }
                                else
                                {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                    }
                });
            }
        }
    }




    private void getEtebar(final int getProgramType)
    {
        final ForoshandehEtebarDAO etebarDAO = new ForoshandehEtebarDAO(mPresenter.getAppContext());

        ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());

        globalCounter = 0;

        if (getProgramType == Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH())
        {
            final ArrayList<String> ccForoshandehs = foroshandehDAO.getDistinctccForoshandeh();

            if (ccForoshandehs.size() > 0)
            {
                for(final String strccForoshandeh : ccForoshandehs)
                {
                    Log.d("GetProgram","Etebar_ccForoshandes:" + ccForoshandehs.toString() + " strccForoshandeh:"+strccForoshandeh);
                    etebarDAO.fetchEtebarForoshandeh(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse()
                    {
                        @Override
                        public void onSuccess(final ArrayList arrayListData)
                        {
                            Thread thread = new Thread()
                            {
                                @Override
                                public void run()
                                {
                                    boolean deleteResult = etebarDAO.deleteByccForoshanhde(Integer.parseInt(strccForoshandeh));
                                    boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                    if (deleteResult && insertResult)
                                    {
                                        globalCounter++;
                                    }
                                    else
                                    {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                                    }
                                    if (globalCounter == ccForoshandehs.size())
                                    {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                        //getEtebarForoshandeh(getProgramType);
                                        //getKala(getProgramType , String.valueOf(ccAfrad));
                                    }
                                }
                            };
                            thread.start();
                        }
                        @Override
                        public void onFailed(String type, String error)
                        {
                            mPresenter.onFailedUpdateEtebarForoshandeh(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        }
                    });
                }
            }
            else
            {
                mPresenter.onFailedUpdateEtebarForoshandeh(++itemCounter , String.format(" type : %1$s \n error : %2$s", "" , ""));
            }
        }
        else
        {
            final ArrayList<ForoshandehModel> foroshandehModels = foroshandehDAO.getAll();

            if (foroshandehModels.size() > 0)
            {
                for(final String strccForoshandeh : foroshandehArray)
                {
                    Log.d("GetProgram","strccForoshandeh:" + strccForoshandeh);
                    etebarDAO.fetchEtebarForoshandeh(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse()
                    {
                        @Override
                        public void onSuccess(final ArrayList arrayListData)
                        {
                            Thread thread = new Thread()
                            {
                                @Override
                                public void run()
                                {
                                    boolean deleteResult = etebarDAO.deleteByccForoshanhde(Integer.parseInt(strccForoshandeh));
                                    boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                    if (deleteResult && insertResult)
                                    {
                                        globalCounter++;
                                    }
                                    else
                                    {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                                    }
                                    if (globalCounter == foroshandehModels.size())
                                    {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                                        getEtebarForoshandeh(getProgramType);
                                        //getKala(getProgramType , String.valueOf(ccAfrad));
                                    }
                                }
                            };
                            thread.start();
                        }
                        @Override
                        public void onFailed(String type, String error)
                        {
                            mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        }
                    });
                }
            }
            else
            {
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                //getKala(getProgramType , String.valueOf(ccAfrad));
                getEtebarForoshandeh(getProgramType);
            }
        }
    }


    private void getEtebarForoshandeh(final int getProgramType)
    {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
        getKala(getProgramType , String.valueOf(ccAfrad), String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
    }

}












