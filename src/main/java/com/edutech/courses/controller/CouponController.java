package com.edutech.courses.controller;

import com.edutech.courses.model.Coupon;
import com.edutech.courses.service.CouponService;
import com.edutech.courses.controller.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/edutech/courses/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // Obtener todos los cupones
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    // Obtener cupón por ID
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Coupon coupon = couponService.getCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    // Obtener cupón por código
    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code) {
        Coupon coupon = couponService.getCouponByCode(code);
        return ResponseEntity.ok(coupon);
    }

    // Crear cupón pasando solo el monto del descuento
    @PostMapping("/add")
    public ResponseEntity<Coupon> createCoupon(@RequestParam BigDecimal discountAmount) {
        return couponService.createCoupon(discountAmount);
    }

    // Actualizar un cupón existente
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody Coupon updatedCoupon) {
        return couponService.updateCouponById(id, updatedCoupon);
    }

    // Eliminar un cupón por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCoupon(@PathVariable Long id) {
        return couponService.deleteCoupon(id);
    }

    // Actualizar el estado de un cupón por código
    @PatchMapping("/{code}/status")
    public ResponseEntity<?> updateCouponStatus(
            @PathVariable String code,
            @RequestParam boolean active
    ) {
        return couponService.updateCouponStatusByCode(code, active);
    }

}
