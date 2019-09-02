package permissions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import character_values.EmployeeRole;

public abstract class PermissionHelper {

	public static Set<EmployeeRole> ANYONE = new HashSet<EmployeeRole>(Arrays.asList(EmployeeRole.values()));
	
	public static Set<EmployeeRole> ADMINISTRATORS_ONLY = new HashSet<EmployeeRole>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -434599120787675779L;

		{
			add(EmployeeRole.ADMINISTRATOR);
		}
	};
	
	public static Set<EmployeeRole> EMPLOYEES = new HashSet<EmployeeRole>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6459015816998909959L;

		{
			add(EmployeeRole.ADMINISTRATOR);
			add(EmployeeRole.EMPLOYEE);
		}
	};
	
	public static boolean permissionGranted(EmployeeRole employeeRole, Set<EmployeeRole> permissions) {
		
		return permissions.contains(employeeRole);
	}
}
