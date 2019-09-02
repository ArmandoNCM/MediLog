package ui.informed_consent;

import entities.Identifiable;

interface IdentifiableSelectionListener {
	void onSelected(Identifiable identifiable, PartSelectionMode selectionMode);
}