package com.saphamrah.MVP.Model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.AddCustomerApplyMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarPishFarzDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryEtebarPishFarzModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryRotbehModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCustomerApplyModel implements AddCustomerApplyMVP.ModelOps
{

    private AddCustomerApplyMVP.RequiredPresenterOps mPresenter;
    private Handler handler;


    public AddCustomerApplyModel(AddCustomerApplyMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAddCustomerInfoModel()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        mPresenter.onGetAddCustomerInfoModel(shared.getAddCustomerInfoModel(shared.JSON_DATA() , ""));
    }

    @Override
    public void insertNewCustomer(final AddCustomerInfoModel addCustomerInfoModel)
    {
		try
        {
            //Log.d("insertCustomer" , "start : " + System.currentTimeMillis());
            final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = getForoshandehMamorPakhsh();
            if (foroshandehMamorPakhshModel == null)
            {
                mPresenter.onFailedInsertNewCustomer(R.string.errorSaveData);
            }
            else
            {
                handler = new android.os.Handler(new android.os.Handler.Callback()
                {
                    @Override
                    public boolean handleMessage(Message msg)
                    {
                        if (msg.arg1 == 1)
                        {
                            mPresenter.onSuccessInsertNewCustomer(addCustomerInfoModel);
                        }
                        else if (msg.arg1 < 0)
                        {
                            mPresenter.onFailedInsertNewCustomer(getErrorMessageId(msg.arg1));
                        }
                        return false;
                    }
                });
				
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int ccPorseshnameh = 1;
                        Log.d("insertCustomer", "before get olaviat");
                        int olaviat = 1;//getOlaviatMoshtary(ccPorseshnameh);
                        Log.d("insertCustomer", "before insert moshtary");
                        MasirDAO masirDao = new MasirDAO(mPresenter.getAppContext());
                        int ccMasir = masirDao.getccMasirByNoeForoshandeh(foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh());
                        long newccMoshtary = insertMoshtary(addCustomerInfoModel, foroshandehMamorPakhshModel, olaviat, ccMasir, ccPorseshnameh);
                        int result = -1;
                        if (newccMoshtary > 0)
                        {
                            Log.d("insertCustomer", "before insertMoshtaryEtebar");
                            result = insertMoshtaryEtebar(newccMoshtary, addCustomerInfoModel.getNoeFaaliatId());
                            if (result == 1)
                            {
                                Log.d("insertCustomer", "before insertMoshtaryRotbeh");
                                result = insertMoshtaryRotbeh(newccMoshtary, Integer.parseInt(addCustomerInfoModel.getRotbeId()));
                                if (result == 1)
                                {
                                    Log.d("insertCustomer", "before insertAddresses");
                                    result = insertAddresses((int) newccMoshtary, addCustomerInfoModel.getMoshtaryAddressModels());
                                    if (result == 1)
                                    {
                                        Log.d("insertCustomer", "before insertShomareHesabs");
                                        String customerName = addCustomerInfoModel.getFirstName() + " " + addCustomerInfoModel.getLastName();
                                        result = insertShomareHesabs((int) newccMoshtary, customerName, addCustomerInfoModel.getMoshtaryShomarehHesabModels());
                                        if (result == 1)
                                        {
                                            Log.d("insertCustomer", "before insertMasirVaznHajmMashin");
                                            result = insertMasirVaznHajmMashin(newccMoshtary, ccMasir);
                                            if (result == 1)
                                            {
                                                Log.d("insertCustomer", "before insertMoshtaryAfrad");
                                                result = insertMoshtaryAfrad(newccMoshtary, addCustomerInfoModel.getFirstName(), addCustomerInfoModel.getLastName());
                                                if (result == 1)
                                                {
                                                    addCustomerInfoModel.setCcMoshtary((int) newccMoshtary);
                                                    sendSuccessMessageToHendler();
                                                }
                                                else
                                                {
                                                    deleteMasirVaznHajmMashin((int) newccMoshtary);
                                                    deleteShomareHesab((int) newccMoshtary);
                                                    deleteAddress((int) newccMoshtary);
                                                    deleteMoshtaryRotbeh((int) newccMoshtary);
                                                    deleteMoshtaryEtebar((int) newccMoshtary);
                                                    deleteMoshtary((int) newccMoshtary);
                                                    sendFailedMessageToHendler(result);
                                                }
                                            }
                                            else
                                            {
                                                deleteShomareHesab((int) newccMoshtary);
                                                deleteAddress((int) newccMoshtary);
                                                deleteMoshtaryRotbeh((int) newccMoshtary);
                                                deleteMoshtaryEtebar((int) newccMoshtary);
                                                deleteMoshtary((int) newccMoshtary);
                                                sendFailedMessageToHendler(result);
                                            }
                                        }
                                        else
                                        {
                                            deleteAddress((int) newccMoshtary);
                                            deleteMoshtaryRotbeh((int) newccMoshtary);
                                            deleteMoshtaryEtebar((int) newccMoshtary);
                                            deleteMoshtary((int) newccMoshtary);
                                            sendFailedMessageToHendler(result);
                                        }
                                    }
                                    else
                                    {
                                        deleteMoshtaryRotbeh((int) newccMoshtary);
                                        deleteMoshtaryEtebar((int) newccMoshtary);
                                        deleteMoshtary((int) newccMoshtary);
                                        sendFailedMessageToHendler(result);
                                    }
                                }
                                else
                                {
                                    deleteMoshtaryEtebar((int) newccMoshtary);
                                    deleteMoshtary((int) newccMoshtary);
                                    sendFailedMessageToHendler(result);
                                }
                            }
                            else
                            {
                                deleteMoshtary((int) newccMoshtary);
                                sendFailedMessageToHendler(result);
                            }
                        }
                        else
                        {
                            deleteMoshtary((int) newccMoshtary);
                            sendFailedMessageToHendler(result);
                        }
                    }
                });
                thread.start();
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "AddCustomerApplyModel", "", "insertNewCustomer", "");
            mPresenter.onFailedInsertNewCustomer(R.string.errorSaveData);
        }
    }

    private void sendFailedMessageToHendler(int errorCode)
    {
        Message message = new Message();
        message.arg1 = errorCode;
        handler.sendMessage(message);
    }


    private int getErrorMessageId(int errorCode)
    {
        switch (errorCode)
        {
            case -1:
                return R.string.errorInsertMoshtaryGoroh;
            case -2:
                return R.string.errorInsertEtebarPishfarzNotDeclare;
            case -3:
                return R.string.errorInsertEtebarPishfarz;
            case -4:
                return R.string.errorInsertMoshtaryRotbeh;
            case -5:
                return R.string.errorInsertMoshtaryAddress;
            case -6:
                return R.string.errorInsertMoshtaryShomareHesab;
            case -7:
                return R.string.errorInsertForoshandehMoshtary;
            case -8:
                return R.string.errorInsertMashin;
            case -9:
                return R.string.errorInsertMoshtaryAfrad;
            default:
                return R.string.errorSaveData;
        }
    }


    private void sendSuccessMessageToHendler()
    {
        Message message = new Message();
        message.arg1 = 1;
        handler.sendMessage(message);
    }


    private long insertMoshtary(AddCustomerInfoModel addCustomerInfoModel, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int olaviat, int ccMasir, int ccPorseshnameh)
    {
        // check that Foroshandeh Is a Sarparast or not? if noeMasoulit == 6 then foroshandeh is a sarparast
        //int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

        try
        {
            String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            MoshtaryModel moshtaryModel = new MoshtaryModel();
            moshtaryModel.setNameMoshtary(addCustomerInfoModel.getFirstName() + " " + addCustomerInfoModel.getLastName());
            moshtaryModel.setNameTablo(addCustomerInfoModel.getTabloName().trim().length() == 0 ? " " : addCustomerInfoModel.getTabloName());
            moshtaryModel.setOlaviat(olaviat);
            moshtaryModel.setCcMasir(ccMasir);
            moshtaryModel.setModateVosol(0);
            moshtaryModel.setMobile(addCustomerInfoModel.getMobile());
            moshtaryModel.setCodeMoshtary("00000");
            moshtaryModel.setCodeNoeVosolAzMoshtary(Integer.parseInt(addCustomerInfoModel.getNoeVosolId()));
            moshtaryModel.setccForoshandeh(foroshandehMamorPakhshModel.getCcForoshandeh());
            moshtaryModel.setCodeNoeHaml(Integer.parseInt(addCustomerInfoModel.getNoeHamlId()));
            moshtaryModel.setFNameMoshtary(addCustomerInfoModel.getFirstName());
            moshtaryModel.setLNameMoshtary(addCustomerInfoModel.getLastName());
            moshtaryModel.setDarajeh(Integer.parseInt(addCustomerInfoModel.getRotbeId()));
            moshtaryModel.setNameDarajeh(addCustomerInfoModel.getRotbeTitle());
            moshtaryModel.setCodeNoeShakhsiat(Integer.parseInt(addCustomerInfoModel.getNoeShakhsiatId()));
            moshtaryModel.setCcNoeSenf(Integer.parseInt(addCustomerInfoModel.getNoeSenfId()));
            moshtaryModel.setCcNoeMoshtary(Integer.parseInt(addCustomerInfoModel.getNoeFaaliatId()));
            moshtaryModel.setExtraProp_IsOld(0);
            moshtaryModel.setExtraProp_ccMoshtaryParent(0);
            moshtaryModel.setCodeVazeiat(0);
            moshtaryModel.setExtraProp_ccPorseshnameh(ccPorseshnameh);
            moshtaryModel.setTarikhMoarefiMoshtary(currentDate);
            if (addCustomerInfoModel.getAnbarId() != null && !addCustomerInfoModel.getAnbarId().trim().equals(""))
            {
                moshtaryModel.setHasAnbar(Integer.parseInt(addCustomerInfoModel.getAnbarId()));
            }
            else
            {
                moshtaryModel.setHasAnbar(0);
            }
            moshtaryModel.setMasahatMaghazeh(Integer.valueOf(addCustomerInfoModel.getMasahatMaghaze().trim().length() == 0 ? "1" : addCustomerInfoModel.getMasahatMaghaze()));
            if (Integer.parseInt(addCustomerInfoModel.getNoeShakhsiatId()) == 1)
            {
                moshtaryModel.setCodeMely(addCustomerInfoModel.getNationalCode().trim().length() == 0 ? " " : addCustomerInfoModel.getNationalCode().trim());
                moshtaryModel.setShenasehMely(" ");
            }
            else
            {
                moshtaryModel.setCodeMely(" ");
                moshtaryModel.setShenasehMely(addCustomerInfoModel.getNationalCode().trim());
            }
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            return moshtaryDAO.insert(moshtaryModel);
        } catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "AddCustomerApplyModel", "", "insertMoshtary", "");
            return -1;
        }
    }
	
    private int insertMoshtaryEtebar(long ccMoshtary, String ccNoeFaaliat)
    {
        MoshtaryEtebarPishFarzDAO moshtaryEtebarPishFarzDAO = new MoshtaryEtebarPishFarzDAO(mPresenter.getAppContext());
        MoshtaryEtebarPishFarzModel moshtaryEtebarPishFarzModel = moshtaryEtebarPishFarzDAO.getByccNoeMoshtary(ccNoeFaaliat);

        if (moshtaryEtebarPishFarzModel.getCcMoshtaryEtebarPishFarz() > 0)
        {
            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = new MoshtaryEtebarSazmanForoshModel();

            moshtaryEtebarSazmanForoshModel.setCcMoshtary((int) ccMoshtary);
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarRiali(moshtaryEtebarPishFarzModel.getSaghfEtebarRiali());
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarAsnad(moshtaryEtebarPishFarzModel.getSaghfEtebarAsnad());
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarTedadi(moshtaryEtebarPishFarzModel.getSaghfEtebarTedadi());
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarModat(moshtaryEtebarPishFarzModel.getSaghfEtebarModat());
            moshtaryEtebarSazmanForoshModel.setEtebarRialAsnadShakhsi(moshtaryEtebarPishFarzModel.getRialAsnadShakhsi());
            moshtaryEtebarSazmanForoshModel.setEtebarTedadAsnadShakhsi(moshtaryEtebarPishFarzModel.getTedadAsnadShakhsi());
            moshtaryEtebarSazmanForoshModel.setEtebarModatAsnadShakhsi(moshtaryEtebarPishFarzModel.getModatAsnadShakhsi());
            moshtaryEtebarSazmanForoshModel.setEtebarRialAsnadMoshtary(moshtaryEtebarPishFarzModel.getRialAsnadMoshtary());
            moshtaryEtebarSazmanForoshModel.setEtebarTedadAsnadMoshtary(moshtaryEtebarPishFarzModel.getTedadAsnadMoshtary());
            moshtaryEtebarSazmanForoshModel.setEtebarModatAsnadMoshtary(moshtaryEtebarPishFarzModel.getModatAsnadMoshtary());
            moshtaryEtebarSazmanForoshModel.setEtebarRialMoavagh(moshtaryEtebarPishFarzModel.getRialMoavagh());
            moshtaryEtebarSazmanForoshModel.setEtebarTedadMoavagh(moshtaryEtebarPishFarzModel.getTedadMoavagh());
            moshtaryEtebarSazmanForoshModel.setEtebarModatMoavagh(moshtaryEtebarPishFarzModel.getModatMoavagh());
            moshtaryEtebarSazmanForoshModel.setEtebarRialBargashty(moshtaryEtebarPishFarzModel.getRialBargashty());
            moshtaryEtebarSazmanForoshModel.setEtebarTedadBargashty(moshtaryEtebarPishFarzModel.getTedadBargashty());
            moshtaryEtebarSazmanForoshModel.setEtebarModatBargashty(moshtaryEtebarPishFarzModel.getModatBargashty());
            moshtaryEtebarSazmanForoshModel.setRialAsnad(0);
            moshtaryEtebarSazmanForoshModel.setTedadAsnad(0);
            moshtaryEtebarSazmanForoshModel.setModatAsnad(0);
            moshtaryEtebarSazmanForoshModel.setRialMoavagh(0);
            moshtaryEtebarSazmanForoshModel.setTedadMoavagh(0);
            moshtaryEtebarSazmanForoshModel.setModatMoavagh(0);
            moshtaryEtebarSazmanForoshModel.setRialBargashty(0);
            moshtaryEtebarSazmanForoshModel.setTedadBargashty(0);
            moshtaryEtebarSazmanForoshModel.setModatBargashty(0);
            moshtaryEtebarSazmanForoshModel.setModatVosol(moshtaryEtebarPishFarzModel.getModatVosol());

            MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
            return moshtaryEtebarSazmanForoshDAO.insert(moshtaryEtebarSazmanForoshModel) ? 1 : -3;
        }
        else
        {
            return -2;
        }
    }

    private int insertMoshtaryRotbeh(long ccMoshtary, int ccDarajeh)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            String currentDate = sdf.format(new Date());
            MoshtaryRotbehModel moshtaryRotbehModel = new MoshtaryRotbehModel();

            moshtaryRotbehModel.setCcMoshtary((int) ccMoshtary);
            moshtaryRotbehModel.setCcBrand(30);
            moshtaryRotbehModel.setDarajeh(ccDarajeh);
            moshtaryRotbehModel.setFromDate(sdf.parse(currentDate));
            moshtaryRotbehModel.setEndDate(sdf.parse(currentDate));
            moshtaryRotbehModel.setNameBrand("");
            moshtaryRotbehModel.setDarsadAfzayeshEtebar(0);

            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
            return moshtaryRotbehDAO.insert(moshtaryRotbehModel) ? 1 : -4;
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "AddCustomerApplyModel", "", "insertMoshtaryRotbeh", "");
            return -4;
        }
    }

    private int insertAddresses(int ccMoshtary, ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        try
        {
            ArrayList<MoshtaryAddressModel> newAddresses = new ArrayList<>();
            for (MoshtaryAddressModel address : moshtaryAddressModels)
            {
                MoshtaryAddressModel newAddress = new MoshtaryAddressModel();

                Log.d("addCustomer", "ccMahale : " + address.getMantagheId());

                newAddress.setCcMoshtaryAddress(0);
                newAddress.setCcMoshtary(ccMoshtary);
                newAddress.setCcAddress(0);
                newAddress.setCcNoeAddress(address.getCcNoeAddress());
                newAddress.setNameNoeAddress(address.getNameNoeAddress());
                newAddress.setAddress(address.getAddress());
                newAddress.setCcShahr(0);
                newAddress.setCcMahaleh(address.getMantagheId());
                newAddress.setKhiabanAsli(address.getKhiabanAsli());
                newAddress.setKhiabanFarei1(address.getKhiabanFarei1());
                newAddress.setKhiabanFarei2(address.getKhiabanFarei2());
                newAddress.setKoocheAsli(address.getKoocheAsli());
                newAddress.setKoocheFarei1(address.getKoocheFarei1());
                newAddress.setKoocheFarei2(address.getKoocheFarei2());
                newAddress.setPelak(address.getPelak());
                newAddress.setTelephone(address.getTelephone());
                newAddress.setCodePosty(address.getCodePosty().trim().length() == 0 ? " " : address.getCodePosty());
                newAddress.setLongitude_x(address.getLongitude_x());
                newAddress.setLatitude_y(address.getLatitude_y());

                Log.d("addCustomer", "ccMahale : " + newAddress.getCcMahaleh());

                newAddresses.add(newAddress);
            }
            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
            return moshtaryAddressDAO.insertGroup(newAddresses) ? 1 : -5;
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "AddCustomerApplyModel", "", "insertAddresses", "");
            return -5;
        }
    }

    private int insertShomareHesabs(int ccMoshtary, String customerName, ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        try
        {
            ArrayList<MoshtaryShomarehHesabModel> newShomarehHesabModels = new ArrayList<>();
            for (MoshtaryShomarehHesabModel shomareHesab : moshtaryShomarehHesabModels)
            {
                MoshtaryShomarehHesabModel newShomareHesab = new MoshtaryShomarehHesabModel();

                newShomareHesab.setCcMoshtary(ccMoshtary);
                newShomareHesab.setCodeMoshtary("00000");
                newShomareHesab.setNameMoshtary(customerName);
                newShomareHesab.setCcBank(shomareHesab.getCcBank());
                newShomareHesab.setNameBank(shomareHesab.getNameBank());
                newShomareHesab.setCcNoeHesab(shomareHesab.getCcNoeHesab());
                newShomareHesab.setNameNoeHesab(shomareHesab.getNameNoeHesab());
                newShomareHesab.setCcShomarehHesab(shomareHesab.getCcShomarehHesab());
                newShomareHesab.setShomarehHesab(shomareHesab.getShomarehHesab());
                newShomareHesab.setNameShobeh(shomareHesab.getNameShobeh());
                newShomareHesab.setCodeShobeh(shomareHesab.getCodeShobeh());
                newShomareHesab.setShartBardashtAzHesab(shomareHesab.getShartBardashtAzHesab());
                newShomareHesab.setSahebanHesab(shomareHesab.getSahebanHesab());

                newShomarehHesabModels.add(newShomareHesab);
            }
            MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
            return moshtaryShomarehHesabDAO.insertGroup(newShomarehHesabModels) ? 1 : -6;
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "AddCustomerApplyModel", "", "insertShomareHesabs", "");
            return -6;
        }
    }

    private int insertMasirVaznHajmMashin(long ccMoshtary, int ccMasir)
    {
        try
        {
            MasirVaznHajmMashinModel masirVaznHajmMashinModel = new MasirVaznHajmMashinModel();

            masirVaznHajmMashinModel.setCcMasir(ccMasir);
            masirVaznHajmMashinModel.setCcMoshtary((int) ccMoshtary);
            masirVaznHajmMashinModel.setVaznMashin(4000);
            masirVaznHajmMashinModel.setHajmMashin((float) 19.73);

            MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
            return masirVaznHajmMashinDAO.insert(masirVaznHajmMashinModel) ? 1 : -8;
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "AddCustomerApplyModel", "", "insertMasirVaznHajmMashin", "");
            return -8;
        }
    }

    private int insertMoshtaryAfrad(long ccMoshtary, String firstName, String lastName)
    {
        try
        {
            String fullnameMoshtary = firstName + " " + lastName;
            MoshtaryAfradModel moshtaryAfradModel = new MoshtaryAfradModel();

            moshtaryAfradModel.setCcMoshtary((int) ccMoshtary);
            moshtaryAfradModel.setCcAfrad(0);
            moshtaryAfradModel.setFullNameMoshtaryAfrad(fullnameMoshtary);
            moshtaryAfradModel.setMojazEmza(true);
            moshtaryAfradModel.setTarafHesab(true);
            moshtaryAfradModel.setFName(firstName);
            moshtaryAfradModel.setLName(lastName);

            MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
            return moshtaryAfradDAO.insert(moshtaryAfradModel) ? 1 : -9;
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "AddCustomerApplyModel", "", "insertMoshtaryAfrad", "");
            return -9;
        }
    }

    private void deleteMasirVaznHajmMashin(int ccMoshtary)
    {
        MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        masirVaznHajmMashinDAO.deleteByccMoshtary(ccMoshtary);
    }

    private void deleteShomareHesab(int ccMoshtary)
    {
        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        moshtaryShomarehHesabDAO.deleteByccMoshtary(ccMoshtary);
    }

    private void deleteAddress(int ccMoshtary)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.deleteByccMoshtary(ccMoshtary);
    }

    private void deleteMoshtaryRotbeh(int ccMoshtary)
    {
        MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
        moshtaryRotbehDAO.deleteByccMoshtaryRotbeh(ccMoshtary);
    }

    private void deleteMoshtaryEtebar(int ccMoshtary)
    {
        MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(ccMoshtary);
    }


    private void deleteMoshtary(int ccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.deleteByccMoshtary(ccMoshtary);
    }

    private ForoshandehMamorPakhshModel getForoshandehMamorPakhsh()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        return foroshandehMamorPakhshDAO.getIsSelect();
    }

    private int getOlaviatMoshtary(int ccPorseshnameh)
    {
        Context context = mPresenter.getAppContext();
        int OlaviatMoshtaryJadid = 0;
        try
        {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(context);
            ArrayList<MoshtaryModel> moshtarys = moshtaryDAO.getAll();
            if (ccPorseshnameh != 0)
            {
                OlaviatMoshtaryJadid = moshtaryDAO.getByccMoshtary(ccPorseshnameh).getOlaviat();
            }
            else
            {
                for (MoshtaryModel moshtary : moshtarys)
                {
                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(context);
                    int darkhastFaktorCount = darkhastFaktorDAO.getCountByccMoshtaryRooz(moshtary.getCcMoshtary());
                    darkhastFaktorCount = darkhastFaktorCount == -1 ? 0 : darkhastFaktorCount;

                    MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
                    int mojoodiGiriCount = mojoodiGiriDAO.getCountByMoshtary(moshtary.getCcMoshtary(), true);
                    mojoodiGiriCount = mojoodiGiriCount == -1 ? 0 : mojoodiGiriCount;

                    AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(context);
                    int adamDarkhastCount = adamDarkhastDAO.getCountByccMoshtary(moshtary.getCcMoshtary());
                    adamDarkhastCount = adamDarkhastCount == -1 ? 0 : adamDarkhastCount;

                    MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(context);
                    int moshtaryMorajehShodehRooz = moshtaryMorajehShodehRoozDAO.getCountByccMoshtary(moshtary.getCcMoshtary());
                    moshtaryMorajehShodehRooz = moshtaryMorajehShodehRooz == -1 ? 0 : moshtaryMorajehShodehRooz;

                    if (darkhastFaktorCount == 0 && mojoodiGiriCount == 0 && adamDarkhastCount == 0)
                    {
                        OlaviatMoshtaryJadid = moshtary.getOlaviat();
                    }
                    if (OlaviatMoshtaryJadid == 1 & moshtaryMorajehShodehRooz > 1)
                    {
                        OlaviatMoshtaryJadid = moshtary.getOlaviat();
                    }
                    if (OlaviatMoshtaryJadid > 0)
                    {
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), AddCustomerApplyModel.class.getSimpleName(), "", "getOlaviatMoshtary", "");
        }
        return OlaviatMoshtaryJadid;
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
