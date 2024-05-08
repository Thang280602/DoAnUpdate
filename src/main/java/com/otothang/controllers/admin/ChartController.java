package com.otothang.controllers.admin;

import com.otothang.Service.OrderSevice;
import com.otothang.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author thang
 */
@Controller
@RequestMapping("/admin")
public class ChartController {
    @Autowired
    private OrderSevice orderService;
    @GetMapping
    public String index() {
        return "redirect:/admin/";
    }
    @RequestMapping("/")
    public String getChartData(Model model) {
        // Lấy danh sách đơn hàng từ service
        List<Order> orders = orderService.getAll(); // Điều chỉnh phương thức này tùy theo cách bạn truy cập dữ liệu

        // Tính tổng số order và tổng tiền cho từng tháng
        Map<String, Double> monthlyTotalPrice = orders.stream()
                .collect(Collectors.groupingBy(order -> getMonthFromDate(order.getCreateAt()),
                        Collectors.summingDouble(Order::getTotalprice)));

        Map<String, Long> monthlyTotalOrders = orders.stream()
                .collect(Collectors.groupingBy(order -> getMonthFromDate(order.getCreateAt()),
                        Collectors.counting()));

        // Tạo mảng labels và mảng data tương ứng
        String[] labels = { "January", "February", "March", "April", "May", "June", "July","August","September","October","November","December" };
        double[] totalPriceData = new double[labels.length];
        long[] totalOrdersData = new long[labels.length];
        for (int i = 0; i < labels.length; i++) {
            String month = labels[i];
            totalPriceData[i] = monthlyTotalPrice.getOrDefault(month, 0.0);
            totalOrdersData[i] = monthlyTotalOrders.getOrDefault(month, 0L);
        }

        // Tạo đối tượng ChartData để trả về
        ChartData chartData = new ChartData(labels, totalPriceData, totalOrdersData);
        model.addAttribute("chartData",chartData);
        return "admin/index";
    }
    private String getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cộng thêm 1 để bắt đầu từ 1
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Invalid month";
        }
    }
    // Định nghĩa một lớp để chứa dữ liệu của biểu đồ
    private static class ChartData {
        private String[] labels;
        private double[] totalPriceData;
        private long[] totalOrdersData;

        public ChartData(String[] labels, double[] totalPriceData, long[] totalOrdersData) {
            this.labels = labels;
            this.totalPriceData = totalPriceData;
            this.totalOrdersData = totalOrdersData;
        }

        public String[] getLabels() {
            return labels;
        }

        public void setLabels(String[] labels) {
            this.labels = labels;
        }

        public double[] getTotalPriceData() {
            return totalPriceData;
        }

        public void setTotalPriceData(double[] totalPriceData) {
            this.totalPriceData = totalPriceData;
        }

        public long[] getTotalOrdersData() {
            return totalOrdersData;
        }

        public void setTotalOrdersData(long[] totalOrdersData) {
            this.totalOrdersData = totalOrdersData;
        }
    }
}
