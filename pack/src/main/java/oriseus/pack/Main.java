package oriseus.pack;

public class Main {

	public static void main(String[] args) {
		App.main(args);

		/*		try {

			StampDTO stampDTO1 = HttpService.getObject("http://localhost:8080/stamps/3%20stamp", StampDTO.class);
			System.out.println(stampDTO1.getName());
			
			StampWrapper<StampDTO> stampWrapper = HttpService.getObject("http://localhost:8080/stamps", StampWrapper.class);
			System.out.println(stampWrapper);
			
			StampDTO stampDTO = new StampDTO();
			stampDTO.setName("7 Stamp");
			stampDTO.setStorageCell("7");
			stampDTO.setTechnologicalMapName("7 map");
			stampDTO.setDamaged(false);
			stampDTO.setRepairPack(true);
			stampDTO.setAvailability(true);
			stampDTO.setDisposal(false);
			stampDTO.setPrice(40500L);
			stampDTO.setNotes("HttpConnectiontest");
			HttpService.sendObject(stampDTO, "http://localhost:8080/stamps/addNewStamp", "POST");  
			HttpService.sendObject(stampDTO, "http://localhost:8080/stamps/delete", "DELETE");  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
*/		
	}

}
