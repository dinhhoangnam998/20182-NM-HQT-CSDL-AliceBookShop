package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Supplier;
import org.alice.bookshop.repository.SupplierJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("amSupplierService")
public class SupplierService {

	@Autowired
	public SupplierJpa supplierJpa;

	public Page<Supplier> getSuppliers(int p, int psize) {
		return supplierJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	private boolean isSupplierExit(Supplier supplier) {
		Supplier isExit = supplierJpa.findByName(supplier.getName());
		return (isExit != null);
	}

	public String add(Supplier supplier) {
		if (isSupplierExit(supplier)) {
			return "Supplier " + supplier.getName() + " already exit!";
		} else {
			supplierJpa.save(supplier);
			return "Add supplier " + supplier.getName() + " successed";
		}
	}

	public String edit(Supplier supplier) {
		Supplier originSupplier = supplierJpa.getOne(supplier.getId());
		if (isSupplierExit(supplier) && !originSupplier.getName().equals(supplier.getName())) {
			return "Supplier's name should not be same with another!";
		} else {
			supplierJpa.save(supplier);
			return "Edit supplier id = " + supplier.getId() + " successed";
		}
	}

}
