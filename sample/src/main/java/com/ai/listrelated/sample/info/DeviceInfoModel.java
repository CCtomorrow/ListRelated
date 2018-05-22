package com.ai.listrelated.sample.info;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/5/21 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class DeviceInfoModel {

    String id;
    String display;
    String product;
    String device;
    String board;
    String manufacturer;
    String brand;
    String model;
    String bootloader;
    String hardware;
    String supported_abis;

    String incremental;
    String release;
    String base_os;
    String security_patch;
    int sdk_int;

    String fingerprint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBootloader() {
        return bootloader;
    }

    public void setBootloader(String bootloader) {
        this.bootloader = bootloader;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getSupported_abis() {
        return supported_abis;
    }

    public void setSupported_abis(String supported_abis) {
        this.supported_abis = supported_abis;
    }

    public String getIncremental() {
        return incremental;
    }

    public void setIncremental(String incremental) {
        this.incremental = incremental;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getBase_os() {
        return base_os;
    }

    public void setBase_os(String base_os) {
        this.base_os = base_os;
    }

    public String getSecurity_patch() {
        return security_patch;
    }

    public void setSecurity_patch(String security_patch) {
        this.security_patch = security_patch;
    }

    public int getSdk_int() {
        return sdk_int;
    }

    public void setSdk_int(int sdk_int) {
        this.sdk_int = sdk_int;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeviceInfoModel{\n");
        sb.append("id='").append(id).append('\'');
        sb.append('\n');
        sb.append("display='").append(display).append('\'');
        sb.append('\n');
        sb.append("product='").append(product).append('\'');
        sb.append('\n');
        sb.append("device='").append(device).append('\'');
        sb.append('\n');
        sb.append("board='").append(board).append('\'');
        sb.append('\n');
        sb.append("manufacturer='").append(manufacturer).append('\'');
        sb.append('\n');
        sb.append("brand='").append(brand).append('\'');
        sb.append('\n');
        sb.append("model='").append(model).append('\'');
        sb.append('\n');
        sb.append("bootloader='").append(bootloader).append('\'');
        sb.append('\n');
        sb.append("hardware='").append(hardware).append('\'');
        sb.append('\n');
        sb.append("supported_abis='").append(supported_abis).append('\'');
        sb.append('\n');
        sb.append("incremental='").append(incremental).append('\'');
        sb.append('\n');
        sb.append("release='").append(release).append('\'');
        sb.append('\n');
        sb.append("base_os='").append(base_os).append('\'');
        sb.append('\n');
        sb.append("security_patch='").append(security_patch).append('\'');
        sb.append('\n');
        sb.append("sdk_int=").append(sdk_int);
        sb.append('\n');
        sb.append("fingerprint='").append(fingerprint).append('\'');
        sb.append('\n');
        sb.append('}');
        return sb.toString();
    }

}
