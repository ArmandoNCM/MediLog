package ui.informed_consent;

import contracts.StringIdentifiable;

interface IdentifiableSelectionListener {
	void onSelected(StringIdentifiable identifiable, PartSelectionMode selectionMode);
}